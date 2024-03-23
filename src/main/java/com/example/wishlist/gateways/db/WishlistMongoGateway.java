package com.example.wishlist.gateways.db;

import com.example.wishlist.domain.Wishlist;

import java.util.Optional;

public interface WishlistMongoGateway {

    Wishlist save(Wishlist wishlist);

    Optional<Wishlist> findByCustomerId(String customerId);
}
