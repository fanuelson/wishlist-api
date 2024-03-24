package com.example.wishlist.usecases;

import com.example.wishlist.gateways.db.WishlistMongoGateway;
import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.gateways.http.exceptions.WishlistNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class ExistsProductInWishlist {

    private final WishlistMongoGateway wishlistMongoGateway;
    public boolean execute(final String customerId,final String productId) {
        return wishlistMongoGateway.findByCustomerId(customerId)
                .map(wishlist1 -> wishlist1.getProducts().stream().anyMatch(p -> p.equalsId(productId)))
                .orElseThrow(WishlistNotFound::new);
    }
}
