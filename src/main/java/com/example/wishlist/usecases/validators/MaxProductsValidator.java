package com.example.wishlist.usecases.validators;

import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.gateways.http.exceptions.WishlistLimitProductsException;
import org.springframework.stereotype.Component;

@Component
public class MaxProductsValidator {

    private static final int PRODUCTS_LIMIT = 20;
    public boolean validate(Wishlist wishlist) {
        if(wishlist.getProducts().size() >= PRODUCTS_LIMIT) {
            throw new WishlistLimitProductsException(PRODUCTS_LIMIT);
        }

        return true;
    }
}
