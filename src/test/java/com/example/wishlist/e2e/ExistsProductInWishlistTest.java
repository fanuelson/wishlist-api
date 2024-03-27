package com.example.wishlist.e2e;

import com.example.wishlist.e2e.config.MongoContainerConfigTest;
import com.example.wishlist.gateways.db.repositories.WishlistMongoRepository;
import com.example.wishlist.gateways.http.dtos.request.ProductRequestDTO;
import com.example.wishlist.mocks.ProductRequestDTOMock;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.Is.is;


@DisplayName("GET /customers/{customerId}/products/{productId}/exists")
class ExistsProductInWishlistTest extends MongoContainerConfigTest {

    @Autowired
    private WishlistMongoRepository wishlistMongoRepository;

    @BeforeEach
    void beforeEach() {
        wishlistMongoRepository.deleteAll();
    }

    @Test
    @Tag("E2E")
    @DisplayName("SHOULD throw NOT FOUND when customer's wishlist doesn't exist")
    void should_throw_not_found() {
        final String customerId = "1";
        final String productId = "1";

        when()
            .get("/wishlist/customers/%s/products/%s/exists".formatted(customerId, productId))
        .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body(
                    "message", is("Wishlist not found")
            );
    }

    @Test
    @Tag("E2E")
    @DisplayName("SHOULD return OK AND false WHEN wishlist exists AND the product doesn't exist")
    void should_return_ok_and_false() {
        final String customerId = "1";
        final String productId = "1";
        final String productIdToSearch = "2";

        final ProductRequestDTO productRequest = ProductRequestDTOMock.create(productId);

        given()
            .contentType(ContentType.JSON)
            .body(productRequest)
        .when()
            .post("/wishlist/customers/%s/products".formatted(customerId))
        .then()
            .statusCode(HttpStatus.SC_CREATED);

        when()
            .get("/wishlist/customers/%s/products/%s/exists".formatted(customerId, productIdToSearch))
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(
                    "exists", is(false)
            );
    }

    @Test
    @Tag("E2E")
    @DisplayName("SHOULD return OK AND true WHEN wishlist exists AND the product exist")
    void should_return_ok_and_true() {
        final String customerId = "1";
        final String productId = "1";
        final String productIdToSearch = "1";

        final ProductRequestDTO productRequest = ProductRequestDTOMock.create(productId);

        given()
            .contentType(ContentType.JSON)
            .body(productRequest)
        .when()
            .post("/wishlist/customers/%s/products".formatted(customerId))
        .then()
            .statusCode(HttpStatus.SC_CREATED);

        when()
            .get("/wishlist/customers/%s/products/%s/exists".formatted(customerId, productIdToSearch))
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(
                    "exists", is(true)
            );
    }

}
