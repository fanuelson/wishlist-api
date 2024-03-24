package com.example.wishlist.gateways.http.exceptions.handler;

import com.example.wishlist.gateways.http.dtos.response.ExceptionResponseDTO;
import com.example.wishlist.gateways.http.exceptions.WishlistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler({WishlistException.class})
    public ResponseEntity<?> handleException(final WishlistException ex) {
        return new ResponseEntity<>(new ExceptionResponseDTO(ex.getMessage()), ex.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleInternalServerException(final Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
