package com.example.wishlist.gateways.http.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class WishlistException extends RuntimeException{

    private HttpStatus status;

    WishlistException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
