package com.example.wishlist.usecases;

import com.example.wishlist.gateways.db.WishlistMongoGateway;
import com.example.wishlist.domain.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class RemoveProductInWishlist {

    private final WishlistMongoGateway wishlistMongoGateway;
    public void execute(final String customerId, final String productId) {
        Wishlist wishlist = wishlistMongoGateway.findByCustomerId(customerId)
                .orElse(Wishlist
                        .builder()
                        .customerId(customerId)
                        .products(List.of()).build()
                );

        wishlist.setProducts(wishlist.getProducts().stream().filter(product -> !product.getId().equals(productId)).toList());
        wishlistMongoGateway.save(wishlist);
    }
}
