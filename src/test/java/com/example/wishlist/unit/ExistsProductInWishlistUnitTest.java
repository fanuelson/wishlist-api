package com.example.wishlist.unit;

import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.exceptions.WishlistNotFoundException;
import com.example.wishlist.gateways.db.WishlistDbGateway;
import com.example.wishlist.mocks.ProductMock;
import com.example.wishlist.mocks.WishlistMock;
import com.example.wishlist.unit.config.BaseUnitTests;
import com.example.wishlist.usecases.ExistsProductInWishlist;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExistsProductInWishlistUnitTest extends BaseUnitTests {

    @InjectMocks
    ExistsProductInWishlist existsProductInWishlist;

    @Mock
    WishlistDbGateway wishlistDbGateway;

    @Test
    @DisplayName("Should throw NOT FOUND WHEN customer's wishlist doesn't exist")
    void shouldThrowNotFoundWhenCustomersWishlistDoesntExist() {
        // Arrange
        final String customerId = "1";
        final String productId = "1";

        when(wishlistDbGateway.findByCustomerId(customerId))
                .thenReturn(Optional.empty());

        // Assert
        assertThrows(WishlistNotFoundException.class, () -> existsProductInWishlist.execute(customerId, productId));
        verify(wishlistDbGateway, times(1)).findByCustomerId(customerId);

    }

    @Test
    @DisplayName("Should return false WHEN product does not exist in customer's wishlist")
    void shouldReturnFalseWhenProductDoesNotExistInCustomersWishlist() {
        // Arrange
        final String customerId = "1";
        final String productId = "1";
        final Wishlist wishlist = WishlistMock.create();

        when(wishlistDbGateway.findByCustomerId(customerId))
                .thenReturn(Optional.of(wishlist));

        final boolean result = existsProductInWishlist.execute(customerId, productId);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true WHEN product exists in customer's wishlist")
    void shouldReturnTrueWhenProductExistsInCustomersWishlist() {
        // Arrange
        final String customerId = "1";
        final String productId = "1";
        final Wishlist wishlist = WishlistMock.create(new ArrayList<>(Arrays.asList(ProductMock.create(productId))));

        when(wishlistDbGateway.findByCustomerId(customerId))
                .thenReturn(Optional.of(wishlist));

        final boolean result = existsProductInWishlist.execute(customerId, productId);

        // Assert
        assertTrue(result);
    }


}
