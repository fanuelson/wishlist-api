package com.example.wishlist.unit;

import com.example.wishlist.domain.Product;
import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.exceptions.ProductNotFoundException;
import com.example.wishlist.exceptions.WishlistNotFoundException;
import com.example.wishlist.gateways.db.WishlistDbGateway;
import com.example.wishlist.mocks.ProductMock;
import com.example.wishlist.mocks.WishlistMock;
import com.example.wishlist.unit.config.BaseUnitTests;
import com.example.wishlist.usecases.FindAllProductsByCustomer;
import com.example.wishlist.usecases.RemoveProductInWishlist;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RemoveProductInWishlistUnitTest extends BaseUnitTests {

    @InjectMocks
    RemoveProductInWishlist removeProductInWishlist;

    @Mock
    WishlistDbGateway wishlistDbGateway;

    @Test
    @DisplayName("Should throw NOT FOUND WHEN trying to remove a product from a non existing wishlist")
    void shouldThrowNotFoundWhenTryingToRemoveAProductFromANonExistingWishlist() {
        // Arrange
        final String customerId = "1";
        final String productId = "1";
        when(wishlistDbGateway.findByCustomerId(customerId))
                .thenReturn(Optional.empty());

        // Act
        assertThrows(
                WishlistNotFoundException.class,
                () -> removeProductInWishlist.execute(customerId, productId)
        );
    }

    @Test
    @DisplayName("Should throw NOT FOUND WHEN trying to remove a non existing product from a existing customer's wishlist")
    void shouldThrowNotFoundWhenTryingToRemoveNonExistingProductFromExistingCustomersWishlist() {
        // Arrange
        final String customerId = "1";
        final String productId = "1";
        final Wishlist wishlist = WishlistMock.create();

        when(wishlistDbGateway.findByCustomerId(customerId))
                .thenReturn(Optional.of(wishlist));

        // Act
        assertThrows(
                ProductNotFoundException.class,
                () -> removeProductInWishlist.execute(customerId, productId)
        );
    }

    @Test
    @DisplayName("Should remove product WHEN trying to remove a existing product from an existing customer's wishlist")
    void shouldRemoveProductWhenTryingToRemoveExistingProductFromExistingWishlist() {
        // Arrange
        final String customerId = "1";
        final String productId = "1";
        final Wishlist wishlist = WishlistMock.create(new ArrayList<>(Arrays.asList(ProductMock.create(productId))));

        when(wishlistDbGateway.findByCustomerId(customerId))
                .thenReturn(Optional.of(wishlist));

        // Act
        final Product productRemoved = removeProductInWishlist.execute(customerId, productId);

        // Assert
        verify(wishlistDbGateway, times(1)).save(wishlist);
        assertEquals("1", productRemoved.getId());
        assertEquals("Product1", productRemoved.getName());
        assertEquals(10.5F, productRemoved.getPrice());
    }

}
