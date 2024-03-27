package com.example.wishlist.gateways.http.handler;

import com.example.wishlist.exceptions.WishlistException;
import com.example.wishlist.gateways.http.dtos.response.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({WishlistException.class})
    public ResponseEntity<ExceptionResponseDTO> handleException(final WishlistException ex) {
        return new ResponseEntity<>(new ExceptionResponseDTO(ex.getMessage()), ex.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleInternalServerException(final Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
