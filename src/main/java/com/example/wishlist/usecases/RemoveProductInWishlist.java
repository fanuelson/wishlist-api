package com.example.wishlist.usecases;

import com.example.wishlist.domain.Product;
import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.exceptions.ProductNotFoundException;
import com.example.wishlist.exceptions.WishlistNotFoundException;
import com.example.wishlist.gateways.db.WishlistDbGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveProductInWishlist {

    private final WishlistDbGateway wishlistDbGateway;

    public Product execute(final String customerId, final String productId) {
        final Wishlist wishlist = wishlistDbGateway.findByCustomerId(customerId)
                .orElseThrow(WishlistNotFoundException::new);

        final Product product = wishlist.findProductById(productId)
                .orElseThrow(ProductNotFoundException::new);

        wishlistDbGateway.save(wishlist.withProductRemoved(product));

        return product;
    }
}
