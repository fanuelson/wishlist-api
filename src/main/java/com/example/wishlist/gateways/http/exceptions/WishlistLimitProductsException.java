package com.example.wishlist.gateways.http.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class WishlistLimitProductsException extends WishlistException{
    public WishlistLimitProductsException() {
        super("Wishlist of this customer has already reached the limit of 20 products");
    }
}
