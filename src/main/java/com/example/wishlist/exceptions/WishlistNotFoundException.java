package com.example.wishlist.exceptions;

import org.springframework.http.HttpStatus;

public class WishlistNotFoundException extends WishlistException {

    static final String WISHLIST_NOT_FOUND_MESSAGE = "Wishlist not found";

    public WishlistNotFoundException() {
        super(WISHLIST_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
    }
}
