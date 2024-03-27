package com.example.wishlist.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WishlistException extends RuntimeException {

    private final HttpStatus status;

    WishlistException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }
}
