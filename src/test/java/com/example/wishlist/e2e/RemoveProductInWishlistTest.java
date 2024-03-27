package com.example.wishlist.e2e;

import com.example.wishlist.e2e.config.MongoContainerConfigTest;
import com.example.wishlist.gateways.db.documents.ProductDocument;
import com.example.wishlist.gateways.db.documents.WishlistDocument;
import com.example.wishlist.gateways.db.repositories.WishlistMongoRepository;
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
import static org.hamcrest.Matchers.any;
import static org.hamcrest.core.Is.is;

@DisplayName("DELETE /customers/{customerId}/products/{productId}")
public class RemoveProductInWishlistTest extends MongoContainerConfigTest {

    @Autowired
    private WishlistMongoRepository wishlistMongoRepository;

    @BeforeEach
    void beforeEach() {
        wishlistMongoRepository.deleteAll();
    }

    @Test
    @Tag("E2E")
    @DisplayName("SHOULD return NOT_FOUND WHEN trying to remove a product from a non existing wishlist")
    void should_return_not_found_when_remove_product_from_non_existing_wishlist() {
        final String customerId = "1";
        final String productId = "1";

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
        final String customerId = "1";
        final String productId = "1";
        final WishlistDocument wishlistDocument = WishlistDocumentMock.create(Collections.emptyList());

        wishlistMongoRepository.save(wishlistDocument);

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
        final String customerId = "1";
        final String productId = "1";

        final ProductDocument product = ProductDocumentMock.create(productId);
        final WishlistDocument wishlistDocument = WishlistDocumentMock.create(List.of(product));

        wishlistMongoRepository.save(wishlistDocument);

        when()
            .delete("/wishlist/customers/%s/products/%s".formatted(customerId, productId))
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(
                    "id", is(any(String.class)),
                    "name", is("Product1"),
                    "price", is(10.5F)
            );
    }
}
