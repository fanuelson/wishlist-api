package com.example.wishlist.repositories;

import com.example.wishlist.entities.Wishlist;
import com.example.wishlist.entities.WishlistId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, WishlistId> {

    List<Wishlist> findByIdCustomerId(String customerId);
}
