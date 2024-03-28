package com.example.wishlist.unit;

import com.example.wishlist.domain.Product;
import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.exceptions.WishlistProductsLimitException;
import com.example.wishlist.gateways.db.WishlistDbGateway;
import com.example.wishlist.mocks.ProductMock;
import com.example.wishlist.mocks.WishlistMock;
import com.example.wishlist.unit.config.BaseUnitTests;
import com.example.wishlist.usecases.AddProductToWishlist;
import com.example.wishlist.usecases.validators.MaxProductsValidator;
import com.example.wishlist.usecases.validators.ValidationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AddProductToWishlistUnitTest extends BaseUnitTests {

    AddProductToWishlist addProductToWishlist;

    @Mock
    WishlistDbGateway wishlistDbGateway;

    MaxProductsValidator maxProductsValidator;

    ValidationProperties validationProperties;

    @BeforeEach
    void setupBeforeEach() {
        validationProperties = new ValidationProperties();
        validationProperties.setMaxProducts(20);
        maxProductsValidator = new MaxProductsValidator(validationProperties);
        addProductToWishlist = new AddProductToWishlist(wishlistDbGateway, maxProductsValidator);
    }

    @Test
    @DisplayName("Should add a product to empty customers wishlist")
    void shouldAddProductToEmptyWishlist() {
        // Arrange
        final String customerId = "1";
        final Product product = ProductMock.create("1");
        final Wishlist wishlist = WishlistMock.create();

        when(wishlistDbGateway.findByCustomerId(customerId))
                .thenReturn(Optional.of(wishlist));

        // Act
        addProductToWishlist.execute(customerId, product);

        // Assert
        verify(wishlistDbGateway, times(1)).findByCustomerId(customerId);
        verify(wishlistDbGateway, times(1)).save(wishlist);
        assertEquals(1, wishlist.getProducts().size());
    }

    @Test
    @DisplayName("Should add a product to customers wishlist with one product")
    void shouldAddProductToWishlistWithOneProduct() {
        // Arrange
        final String customerId = "1";
        final Product product = ProductMock.create("1");
        final Wishlist wishlist = WishlistMock.create(new ArrayList<>(Arrays.asList(ProductMock.create("2"))));

        when(wishlistDbGateway.findByCustomerId(customerId))
                .thenReturn(Optional.of(wishlist));

        // Act
        addProductToWishlist.execute(customerId, product);

        // Assert
        verify(wishlistDbGateway, times(1)).findByCustomerId(customerId);
        verify(wishlistDbGateway, times(1)).save(wishlist);
        assertEquals(2, wishlist.getProducts().size());
    }

    @Test
    @DisplayName("Should update a product of customers wishlist with one product")
    void shouldUpdateProductOfWishlistWithOneProduct() {
        // Arrange
        final String customerId = "1";
        final Product product = ProductMock.create("1", "Product name updated");
        final Wishlist wishlist = WishlistMock.create(new ArrayList<>(Arrays.asList(ProductMock.create("1"))));

        when(wishlistDbGateway.findByCustomerId(customerId))
                .thenReturn(Optional.of(wishlist));

        // Act
        addProductToWishlist.execute(customerId, product);

        // Assert
        assertEquals("Product name updated", wishlist.getProducts().getFirst().getName());
    }

    @Test
    @DisplayName("Should throw error WHEN add a product to customers wishlist with max products")
    void shouldThrowErrorWhenWishlistWithMaxProduct() {
        // Arrange
        final String customerId = "1";
        final Product product = ProductMock.create("1");
        final List<Product> products = range(0, 20)
                .mapToObj(String::valueOf)
                .map(ProductMock::create)
                .toList();
        final Wishlist wishlist = WishlistMock.create(new ArrayList<>(products));

        when(wishlistDbGateway.findByCustomerId(customerId))
                .thenReturn(Optional.of(wishlist));


        // Assert
        assertThrows(WishlistProductsLimitException.class, () -> addProductToWishlist.execute(customerId, product));
    }
}
