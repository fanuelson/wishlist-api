package com.example.wishlist.usecases.validators;

import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.exceptions.WishlistProductsLimitException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MaxProductsValidator {

    private final ValidationProperties validationProperties;

    public void validate(final Wishlist wishlist) {
        final int maxProducts = validationProperties.getMaxProducts();

        if (wishlist.getProducts().size() >= maxProducts) {
            throw new WishlistProductsLimitException(maxProducts);
        }
    }
}
