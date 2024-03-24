package com.example.wishlist.gateways.http.exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFound extends WishlistException {

    static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    public ProductNotFound() {
        super(PRODUCT_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
    }
}
