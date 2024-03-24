package com.example.wishlist.gateways.http.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class WishlistLimitProductsException extends WishlistException {

    static final String PRODUCTS_LIMIT_ERROR_MESSAGE = "Wishlist of this customer has already reached the limit of %s products";
    public WishlistLimitProductsException(final int maxProductsLimit) {
        super(PRODUCTS_LIMIT_ERROR_MESSAGE.formatted(maxProductsLimit), HttpStatus.BAD_REQUEST);
    }
}
