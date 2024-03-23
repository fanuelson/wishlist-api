package com.example.wishlist.usecases;

import com.example.wishlist.gateways.db.WishlistMongoGateway;
import com.example.wishlist.domain.Product;
import com.example.wishlist.domain.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class FindAllProductsByCustomer {

    private final WishlistMongoGateway wishlistMongoGateway;
    public List<Product> execute(final String customerId) {
        Wishlist wishlist = wishlistMongoGateway.findByCustomerId(customerId)
                .orElse(Wishlist
                        .builder()
                        .customerId(customerId)
                        .products(List.of()).build()
                );


        return wishlist.getProducts();
    }
}