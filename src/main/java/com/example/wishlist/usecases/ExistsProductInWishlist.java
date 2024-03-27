package com.example.wishlist.usecases;

import com.example.wishlist.exceptions.WishlistNotFoundException;
import com.example.wishlist.gateways.db.WishlistDbGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistsProductInWishlist {

    private final WishlistDbGateway wishlistDbGateway;

    public boolean execute(final String customerId, final String productId) {
        return wishlistDbGateway.findByCustomerId(customerId)
                .map(wishlist -> wishlist.findProductById(productId).isPresent())
                .orElseThrow(WishlistNotFoundException::new);
    }
}
