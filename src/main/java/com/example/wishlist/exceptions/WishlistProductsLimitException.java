package com.example.wishlist.exceptions;

import org.springframework.http.HttpStatus;

public class WishlistProductsLimitException extends WishlistException {

    static final String PRODUCTS_LIMIT_ERROR_MESSAGE = "Wishlist of this customer has already reached the limit of %s products";

    public WishlistProductsLimitException(final int maxProductsLimit) {
        super(PRODUCTS_LIMIT_ERROR_MESSAGE.formatted(maxProductsLimit), HttpStatus.BAD_REQUEST);
    }
}
