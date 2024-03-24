package com.example.wishlist.e2e;

import com.example.wishlist.domain.Product;
import com.example.wishlist.gateways.db.documents.ProductDocument;
import com.example.wishlist.gateways.db.documents.WishlistDocument;
import com.example.wishlist.gateways.db.repositories.WishlistRepository;
import com.example.wishlist.gateways.http.dtos.request.ProductRequestDTO;
import com.example.wishlist.gateways.http.dtos.response.ProductResponseDTO;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@DisplayName("GET /customers/{customerId}/products")
public class FindAllProductsByCustomerTest {

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:7.0.7"));

    @Autowired
    private WishlistRepository wishlistRepository;

    @BeforeEach
    void beforeEach() {
        wishlistRepository.deleteAll();
    }

    @Test
    @Tag("E2E")
    @DisplayName("SHOULD return OK AND empty products WHEN customer's wishlist doesn't exist")
    void should_return_empty_products_when_wishlist_doesnt_exist() {
        String customerId = "1";
        when()
            .get("/wishlist/customers/%s/products".formatted(customerId))
        .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.SC_OK)
            .body(equalTo("[]"));
    }

    @Test
    @Tag("E2E")
    @DisplayName("SHOULD return OK AND empty products WHEN customer's wishlist have no products")
    void should_return_empty_products_when_wishlist_exist_with_no_products() {
        String customerId = "1";
        WishlistDocument wishlistDocument = new WishlistDocument();
        wishlistDocument.setCustomerId("1");
        wishlistDocument.setProducts(Collections.emptyList());
        wishlistRepository.save(wishlistDocument);

        when()
            .get("/wishlist/customers/%s/products".formatted(customerId))
        .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.SC_OK)
            .body(equalTo("[]"));
    }

    @Test
    @Tag("E2E")
    @DisplayName("SHOULD return OK AND one product WHEN wishlist exists AND have 1 product")
    void should_return_ok_and_one_product() {
        String customerId = "1";
        String productId = "1";

        ProductDocument product = new ProductDocument();
        product.setId(productId);
        product.setName("Product A");
        product.setPrice(10.2);

        WishlistDocument wishlistDocument = new WishlistDocument();
        wishlistDocument.setCustomerId("1");
        wishlistDocument.setProducts(List.of(product));
        wishlistRepository.save(wishlistDocument);

        ArrayList<ProductResponseDTO> products = when()
            .get("/wishlist/customers/%s/products".formatted(customerId))
            .as(ArrayList.class);

        assertThat(products.size()).isEqualTo(1);
    }

}
