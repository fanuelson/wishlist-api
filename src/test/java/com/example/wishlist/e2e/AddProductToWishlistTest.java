package com.example.wishlist.e2e;

import com.example.wishlist.e2e.config.MongoContainerConfigTest;
import com.example.wishlist.gateways.db.documents.ProductDocument;
import com.example.wishlist.gateways.db.documents.WishlistDocument;
import com.example.wishlist.gateways.db.repositories.WishlistMongoRepository;
import com.example.wishlist.gateways.http.dtos.request.ProductRequestDTO;
import com.example.wishlist.mocks.ProductDocumentMock;
import com.example.wishlist.mocks.ProductRequestDTOMock;
import com.example.wishlist.mocks.WishlistDocumentMock;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("POST customers/{customerId}/products")
class AddProductToWishlistTest extends MongoContainerConfigTest {

    @Autowired
    private WishlistMongoRepository wishlistMongoRepository;

    @BeforeEach
    void beforeEach() {
        wishlistMongoRepository.deleteAll();
    }

    @Test
    @Tag("E2E")
    @DisplayName("SHOULD create a new wishlist with a product")
    void new_wishlist_with_a_new_product() {
        final ProductRequestDTO productRequest = ProductRequestDTOMock.create();

        given()
            .contentType(ContentType.JSON)
            .body(productRequest)
        .when()
            .post("/wishlist/customers/1/products")
        .then()
            .statusCode(HttpStatus.SC_CREATED);

        final Optional<WishlistDocument> wishlistDocumentOpt = wishlistMongoRepository.findByCustomerId("1");
        assertThat(wishlistDocumentOpt).isNotEmpty();
        assertThat(wishlistDocumentOpt.get().getId()).isInstanceOfAny(String.class);
    }

    @Test
    @Tag("E2E")
    @DisplayName("SHOULD add a product to an existing wishlist")
    void add_product_to_existing_wishlist() {
        final ProductRequestDTO productRequest = ProductRequestDTOMock.create("1");

        given()
            .contentType(ContentType.JSON)
            .body(productRequest)
        .when()
            .post("/wishlist/customers/1/products")
        .then()
            .statusCode(HttpStatus.SC_CREATED);

        final ProductRequestDTO productRequest2 = ProductRequestDTOMock.create("2");

        given()
            .contentType(ContentType.JSON)
            .body(productRequest2)
        .when()
            .post("/wishlist/customers/1/products")
        .then()
            .statusCode(HttpStatus.SC_CREATED);

        final Optional<WishlistDocument> wishlistDocumentOpt = wishlistMongoRepository.findByCustomerId("1");
        assertThat(wishlistDocumentOpt).isNotEmpty();
        assertThat(wishlistDocumentOpt.get().getId()).isInstanceOfAny(String.class);
        assertThat(wishlistDocumentOpt.get().getProducts()).hasSize(2);
    }

    @Test
    @Tag("E2E")
    @DisplayName("SHOULD throw error when adding a product to an existing wishlist with 20 products")
    void add_product_to_existing_wishlist_with_20_products() {
        final List<ProductDocument> products = range(0, 20)
                .mapToObj(String::valueOf)
                .map(ProductDocumentMock::create)
                .toList();
        final WishlistDocument wishlistDocument = WishlistDocumentMock.create(products);

        wishlistMongoRepository.save(wishlistDocument);

        final ProductRequestDTO productRequest = ProductRequestDTOMock.create("1");

        given()
            .contentType(ContentType.JSON)
        .body(productRequest).when().post("/wishlist/customers/1/products")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);

        final Optional<WishlistDocument> wisthlistDocumentOpt = wishlistMongoRepository.findByCustomerId("1");
        assertThat(wisthlistDocumentOpt).isNotEmpty();
        assertThat(wisthlistDocumentOpt.get().getId()).isInstanceOfAny(String.class);
        assertThat(wisthlistDocumentOpt.get().getProducts()).hasSize(20);
    }
}
