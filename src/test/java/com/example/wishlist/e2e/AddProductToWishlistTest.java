package com.example.wishlist.e2e;

import com.example.wishlist.domain.Product;
import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.gateways.db.documents.ProductDocument;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @DisplayName("SHOULD create a new wishlist with a product")
    void new_wishlist_with_a_new_product() {
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

    @Test
    @Tag("E2E")
    @DisplayName("SHOULD add a product to an existing wishlist")
    void add_product_to_existing_wishlist() {
        ProductRequestDTO productRequest = new ProductRequestDTO();
        productRequest.setId("1");
        productRequest.setName("Product 1");
        productRequest.setPrice(10.5);

        given()
            .contentType(ContentType.JSON)
        .body(productRequest).when().post("/wishlist/customers/1/products")
            .then()
            .statusCode(HttpStatus.SC_CREATED);

        ProductRequestDTO productRequest2 = new ProductRequestDTO();
        productRequest.setId("2");
        productRequest.setName("Product 2");
        productRequest.setPrice(20.5);

        given()
            .contentType(ContentType.JSON)
        .body(productRequest2).when().post("/wishlist/customers/1/products")
            .then()
            .statusCode(HttpStatus.SC_CREATED);

        Optional<WishlistDocument> wishlistDocument = wishlistRepository.findByCustomerId("1");
        assertThat(wishlistDocument).isNotEmpty();
        assertThat(wishlistDocument.get().getId()).isInstanceOfAny(String.class);
        assertThat(wishlistDocument.get().getProducts().size()).isEqualTo(2);
    }

    @Test
    @Tag("E2E")
    @DisplayName("SHOULD throw error when adding a product to an existing wishlist with 20 products")
    void add_product_to_existing_wishlist_with_20_products() {
        List<ProductDocument> products = new ArrayList<>();
        for(int i = 0;i < 20;i++) {
            products.add(
                    ProductDocument.create(Product.builder()
                            .id(""+i)
                            .name("Product "+i)
                            .price(30.5)
                            .build()
                    )
            );
        }

        WishlistDocument wishlistDocument = new WishlistDocument();
        wishlistDocument.setCustomerId("1");
        wishlistDocument.setProducts(products);

        wishlistRepository.save(wishlistDocument);

        ProductRequestDTO productRequest = new ProductRequestDTO();
        productRequest.setId("2");
        productRequest.setName("Product 2");
        productRequest.setPrice(20.5);

        given()
                .contentType(ContentType.JSON)
                .body(productRequest).when().post("/wishlist/customers/1/products")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);

        Optional<WishlistDocument> wishlistDocumentDB = wishlistRepository.findByCustomerId("1");
        assertThat(wishlistDocumentDB).isNotEmpty();
        assertThat(wishlistDocumentDB.get().getId()).isInstanceOfAny(String.class);
        assertThat(wishlistDocumentDB.get().getProducts().size()).isEqualTo(20);
    }
}
