package com.example.wishlist.gateways.db;

import com.example.wishlist.domain.Wishlist;

import java.util.Optional;

public interface WishlistDbGateway {

    Wishlist save(final Wishlist wishlist);

    Optional<Wishlist> findByCustomerId(final String customerId);
}
