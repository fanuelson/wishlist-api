package com.example.wishlist.e2e;

import com.example.wishlist.gateways.db.documents.WishlistDocument;
import com.example.wishlist.gateways.db.repositories.WishlistRepository;
import com.example.wishlist.gateways.http.dtos.request.ProductRequestDTO;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.any;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@DisplayName("POST customers/{customerId}/products")
public class AddProductToWishlistTest {

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
    @DisplayName("SHOULD create a new product")
    void whenPost() {
        ProductRequestDTO productRequest = new ProductRequestDTO();
        productRequest.setId("1");
        productRequest.setName("Product 1");
        productRequest.setPrice(10.5);

        given()
            .contentType(ContentType.JSON)
            .body(productRequest).when().post("/wishlist/customers/1/products")
        .then()
            .statusCode(HttpStatus.SC_CREATED);

        Optional<WishlistDocument> wishlistDocument = wishlistRepository.findByCustomerId("1");
        assertThat(wishlistDocument).isNotEmpty();
        assertThat(wishlistDocument.get().getId()).isInstanceOfAny(String.class);
    }
}
