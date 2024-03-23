package com.example.wishlist.gateways.db.repositories;


import com.example.wishlist.gateways.db.documents.WishlistDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends MongoRepository<WishlistDocument, String> {

    Optional<WishlistDocument> findByCustomerId(String customerId);
}
