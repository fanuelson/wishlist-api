package com.example.wishlist.gateways.db;

import com.example.wishlist.gateways.db.documents.WishlistDocument;
import com.example.wishlist.gateways.db.repositories.WishlistRepository;
import com.example.wishlist.domain.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WishlistMongoGatewayImpl implements WishlistMongoGateway{

    private final WishlistRepository wishlistRepository;
    @Override
    public Wishlist save(Wishlist wishlist) {
        WishlistDocument wishlistDocument = wishlistRepository.save(WishlistDocument.create(wishlist));
        return Wishlist.create(wishlistDocument);
    }

    @Override
    public Optional<Wishlist> findByCustomerId(String customerId) {
        Optional<WishlistDocument> wishlist = wishlistRepository.findByCustomerId(customerId);
        return wishlist.map(Wishlist::create);
    }
}
