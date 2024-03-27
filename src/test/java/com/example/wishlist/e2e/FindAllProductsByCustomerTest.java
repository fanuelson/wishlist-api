package com.example.wishlist.e2e;

import com.example.wishlist.e2e.config.MongoContainerConfigTest;
import com.example.wishlist.gateways.db.documents.ProductDocument;
import com.example.wishlist.gateways.db.documents.WishlistDocument;
import com.example.wishlist.gateways.db.repositories.WishlistMongoRepository;
import com.example.wishlist.gateways.http.dtos.response.ProductResponseDTO;
import com.example.wishlist.mocks.ProductDocumentMock;
import com.example.wishlist.mocks.WishlistDocumentMock;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;


@DisplayName("GET /customers/{customerId}/products")
public class FindAllProductsByCustomerTest extends MongoContainerConfigTest {

    @Autowired
    private WishlistMongoRepository wishlistMongoRepository;

    @BeforeEach
    void beforeEach() {
        wishlistMongoRepository.deleteAll();
    }

    @Test
    @Tag("E2E")
    @DisplayName("SHOULD return OK AND empty products WHEN customer's wishlist doesn't exist")
    void should_return_empty_products_when_wishlist_doesnt_exist() {
        final String customerId = "1";
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
        final String customerId = "1";
        final WishlistDocument wishlistDocument = WishlistDocumentMock.create(Collections.emptyList());

        wishlistMongoRepository.save(wishlistDocument);

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
        final String customerId = "1";
        final String productId = "1";

        final ProductDocument product = ProductDocumentMock.create(productId);
        final WishlistDocument wishlistDocument = WishlistDocumentMock.create(List.of(product));

        wishlistMongoRepository.save(wishlistDocument);

        final List<ProductResponseDTO> products = when()
                .get("/wishlist/customers/%s/products".formatted(customerId))
                .<List<ProductResponseDTO>>as(List.class);

        assertThat(products).hasSize(1);
    }
}
