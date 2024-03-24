package com.example.wishlist.gateways.http.exceptions;

import org.springframework.http.HttpStatus;

public class WishlistNotFound extends WishlistException {

    static final String WISHLIST_NOT_FOUND_MESSAGE = "Wishlist not found";
    public WishlistNotFound() {
        super(WISHLIST_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
    }
}
