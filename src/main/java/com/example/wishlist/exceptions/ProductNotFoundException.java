package com.example.wishlist.exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends WishlistException {

    static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";

    public ProductNotFoundException() {
        super(PRODUCT_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
    }
}
