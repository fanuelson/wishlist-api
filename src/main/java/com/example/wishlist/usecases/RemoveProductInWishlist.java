package com.example.wishlist.usecases;

import com.example.wishlist.domain.Product;
import com.example.wishlist.gateways.db.WishlistMongoGateway;
import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.gateways.http.exceptions.ProductNotFound;
import com.example.wishlist.gateways.http.exceptions.WishlistNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class RemoveProductInWishlist {

    private final WishlistMongoGateway wishlistMongoGateway;
    public Product execute(final String customerId, final String productId) {
        Wishlist wishlist = wishlistMongoGateway.findByCustomerId(customerId)
                .orElseThrow(WishlistNotFound::new);

        Product product = wishlist.findProductById(productId)
                .orElseThrow(ProductNotFound::new);

        wishlistMongoGateway.save(wishlist.withProductRemoved(product));

        return product;
    }
}
