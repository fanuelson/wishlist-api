package com.example.wishlist.usecases;

import com.example.wishlist.domain.Product;
import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.gateways.db.WishlistDbGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAllProductsByCustomer {

    private final WishlistDbGateway wishlistDbGateway;

    public List<Product> execute(final String customerId) {
        return wishlistDbGateway.findByCustomerId(customerId)
                .map(Wishlist::getProducts)
                .orElse(Collections.emptyList());
    }
}
