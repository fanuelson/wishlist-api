package com.example.wishlist.usecases;

import com.example.wishlist.gateways.db.WishlistMongoGateway;
import com.example.wishlist.domain.Product;
import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.gateways.http.exceptions.WishlistLimitProductsException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddProductToWishlist {

    private static final int PRODUCTS_LIMIT = 20;

    private final WishlistMongoGateway wishlistMongoGateway;
    public Wishlist execute(final String customerId,final Product product) {
        Wishlist wishlist = wishlistMongoGateway.findByCustomerId(customerId)
                .orElse(Wishlist.create(customerId));

        if(wishlist.getProducts().size() >= PRODUCTS_LIMIT) {
            throw new WishlistLimitProductsException(PRODUCTS_LIMIT);
        }
        int indexOfProduct = wishlist.getProducts().indexOf(product);
        if(indexOfProduct >= 0) {
            wishlist.getProducts().set(indexOfProduct, product);
        } else {
            wishlist.getProducts().addFirst(product);
        }

        return wishlistMongoGateway.save(wishlist);
    }
}
