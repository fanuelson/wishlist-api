package com.example.wishlist.usecases;

import com.example.wishlist.domain.Product;
import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.gateways.db.WishlistDbGateway;
import com.example.wishlist.usecases.validators.MaxProductsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddProductToWishlist {

    private final WishlistDbGateway wishlistDbGateway;
    private final MaxProductsValidator maxProductsValidator;

    public Wishlist execute(final String customerId, final Product product) {
        final Wishlist wishlist = wishlistDbGateway.findByCustomerId(customerId)
                .orElse(Wishlist.create(customerId));
        maxProductsValidator.validate(wishlist);

        wishlist.addOrUpdateProduct(product);
        return wishlistDbGateway.save(wishlist);
    }
}
