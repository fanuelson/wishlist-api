package com.example.wishlist.usecases;

import com.example.wishlist.gateways.db.WishlistMongoGateway;
import com.example.wishlist.domain.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class ExistsProductInWishlist {

    private final WishlistMongoGateway wishlistMongoGateway;
    public boolean execute(final String customerId,final String productId) {
        Wishlist wishlist = wishlistMongoGateway.findByCustomerId(customerId)
                .orElse(Wishlist
                        .builder()
                        .customerId(customerId)
                        .products(List.of()).build()
                );


        return wishlist.getProducts().stream().anyMatch((product -> product.getId().equals(productId)));
    }
}
