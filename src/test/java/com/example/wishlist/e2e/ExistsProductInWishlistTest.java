package com.example.wishlist.e2e;

import com.example.wishlist.domain.Product;
import com.example.wishlist.gateways.db.documents.ProductDocument;
import com.example.wishlist.gateways.db.documents.WishlistDocument;
import com.example.wishlist.gateways.db.repositories.WishlistRepository;
import com.example.wishlist.gateways.http.dtos.request.ProductRequestDTO;
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
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@DisplayName("GET /customers/{customerId}/products/{productId}/exists")
public class ExistsProductInWishlistTest {

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
    @DisplayName("SHOULD throw NOT FOUND when customer's wishlist doesn't exist")
    void should_throw_not_found() {
        String customerId = "1";
        String productId = "1";
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
        String customerId = "1";
        String productId = "1";
        String productIdToSearch = "2";
        ProductRequestDTO productRequest = new ProductRequestDTO();
        productRequest.setId(productId);
        productRequest.setName("Product 1");
        productRequest.setPrice(10.5);

        given()
            .contentType(ContentType.JSON)
        .body(productRequest).when().post("/wishlist/customers/%s/products".formatted(customerId))
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
        String customerId = "1";
        String productId = "1";
        String productIdToSearch = "1";
        ProductRequestDTO productRequest = new ProductRequestDTO();
        productRequest.setId(productId);
        productRequest.setName("Product 1");
        productRequest.setPrice(10.5);

        given()
                .contentType(ContentType.JSON)
                .body(productRequest).when().post("/wishlist/customers/%s/products".formatted(customerId))
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
