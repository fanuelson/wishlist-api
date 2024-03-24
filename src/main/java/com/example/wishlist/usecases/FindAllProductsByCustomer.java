package com.example.wishlist.usecases;

import com.example.wishlist.gateways.db.WishlistMongoGateway;
import com.example.wishlist.domain.Product;
import com.example.wishlist.domain.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
@Component
@RequiredArgsConstructor
public class FindAllProductsByCustomer {

    private final WishlistMongoGateway wishlistMongoGateway;
    public List<Product> execute(final String customerId) {
        return wishlistMongoGateway.findByCustomerId(customerId)
                .map(Wishlist::getProducts)
                .orElse(Collections.emptyList());
    }
}
