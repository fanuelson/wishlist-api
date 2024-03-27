package com.example.wishlist.gateways.db.repositories;


import com.example.wishlist.gateways.db.documents.WishlistDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistMongoRepository extends MongoRepository<WishlistDocument, String> {

    Optional<WishlistDocument> findByCustomerId(final String customerId);
}
