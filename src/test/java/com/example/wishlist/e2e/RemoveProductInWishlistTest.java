package com.example.wishlist.e2e;

import com.example.wishlist.gateways.db.documents.ProductDocument;
import com.example.wishlist.gateways.db.documents.WishlistDocument;
import com.example.wishlist.gateways.db.repositories.WishlistRepository;
import com.example.wishlist.gateways.http.dtos.response.ProductResponseDTO;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
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
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@DisplayName("DELETE /customers/{customerId}/products/{productId}")
public class RemoveProductInWishlistTest {

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
    @DisplayName("SHOULD return NOT_FOUND WHEN trying to remove a product from a non existing wishlist")
    void should_return_not_found_when_remove_product_from_non_existing_wishlist() {
        String customerId = "1";
        String productId = "1";
        when()
            .delete("/wishlist/customers/%s/products/%s".formatted(customerId, productId))
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body(
                    "message", is("Wishlist not found")
            );
    }

    @Test
    @Tag("E2E")
    @DisplayName("SHOULD return NOT_FOUND WHEN trying to remove non existing product from existing wishlist")
    void should_return_not_found_when_trying_remove_non_existing_product_from_existing_wishlist() {
        String customerId = "1";
        String productId = "1";
        WishlistDocument wishlistDocument = new WishlistDocument();
        wishlistDocument.setCustomerId("1");
        wishlistDocument.setProducts(Collections.emptyList());
        wishlistRepository.save(wishlistDocument);

        when()
            .delete("/wishlist/customers/%s/products/%s".formatted(customerId, productId))
        .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body(
            "message", is("Product not found")
            );
    }

    @Test
    @Tag("E2E")
    @DisplayName("SHOULD return OK AND the product removed WHEN removing existing product from existing wishlist")
    void should_return_ok_and_product_removed() {
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

         when()
            .delete("/wishlist/customers/%s/products/%s".formatted(customerId, productId))
         .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(
                "id", is(any(String.class)),
                    "name", is("Product A"),
                    "price", is(10.2F)
            );
    }

}
