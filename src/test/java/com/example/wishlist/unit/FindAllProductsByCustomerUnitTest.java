package com.example.wishlist.unit;

import com.example.wishlist.domain.Product;
import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.exceptions.WishlistNotFoundException;
import com.example.wishlist.gateways.db.WishlistDbGateway;
import com.example.wishlist.mocks.ProductMock;
import com.example.wishlist.mocks.WishlistMock;
import com.example.wishlist.unit.config.BaseUnitTests;
import com.example.wishlist.usecases.ExistsProductInWishlist;
import com.example.wishlist.usecases.FindAllProductsByCustomer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindAllProductsByCustomerUnitTest extends BaseUnitTests {

    @InjectMocks
    FindAllProductsByCustomer findAllProductsByCustomer;

    @Mock
    WishlistDbGateway wishlistDbGateway;

    @Test
    @DisplayName("Should return empty products WHEN customer's wishlist doesn't exist")
    void shouldReturnEmptyProductsWhenCustomersWishlistDoesntExist() {
        // Arrange
        final String customerId = "1";

        when(wishlistDbGateway.findByCustomerId(customerId))
                .thenReturn(Optional.empty());

        // Act
        final List<Product> products = findAllProductsByCustomer.execute(customerId);

        // Assert
        assertEquals(0, products.size());
    }

    @Test
    @DisplayName("Should return empty products WHEN customer's wishlist have no products")
    void shouldReturnEmptyProductsWhenCustomersWishlistHaveNoProducts() {
        // Arrange
        final String customerId = "1";
        final Wishlist wishlist = WishlistMock.create();

        when(wishlistDbGateway.findByCustomerId(customerId))
                .thenReturn(Optional.of(wishlist));

        // Act
        final List<Product> products = findAllProductsByCustomer.execute(customerId);

        // Assert
        assertEquals(0, products.size());
    }

    @Test
    @DisplayName("Should return one product WHEN customer's wishlist have one products")
    void shouldReturnOneProductWhenCustomersWishlistHaveOneProduct() {
        // Arrange
        final String customerId = "1";
        final Wishlist wishlist = WishlistMock.create(new ArrayList<>(Arrays.asList(ProductMock.create("1"))));

        when(wishlistDbGateway.findByCustomerId(customerId))
                .thenReturn(Optional.of(wishlist));

        // Act
        final List<Product> products = findAllProductsByCustomer.execute(customerId);

        // Assert
        assertEquals(1, products.size());
        Product product = products.getFirst();
        assertEquals("1", product.getId());
        assertEquals("Product1", product.getName());
        assertEquals(10.5F, product.getPrice());
    }

}
