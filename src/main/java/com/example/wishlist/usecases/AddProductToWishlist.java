package com.example.wishlist.usecases;

import com.example.wishlist.gateways.db.WishlistMongoGateway;
import com.example.wishlist.domain.Product;
import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.gateways.http.exceptions.WishlistLimitProductsException;
import com.example.wishlist.usecases.validators.MaxProductsValidator;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddProductToWishlist {

    private final MaxProductsValidator maxProductsValidator;

    private final WishlistMongoGateway wishlistMongoGateway;
    public Wishlist execute(final String customerId,final Product product) {
        Wishlist wishlist = wishlistMongoGateway.findByCustomerId(customerId)
                .orElse(Wishlist.create(customerId));
        this.maxProductsValidator.validate(wishlist);
        wishlist.addOrUpdateProduct(product);
        return wishlistMongoGateway.save(wishlist);
    }
}
