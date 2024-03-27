package com.example.wishlist.gateways.db;

import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.gateways.db.documents.WishlistDocument;
import com.example.wishlist.gateways.db.repositories.WishlistMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WishlistMongoGatewayImpl implements WishlistDbGateway {

    private final WishlistMongoRepository wishlistMongoRepository;

    @Override
    public Wishlist save(final Wishlist wishlist) {
        final WishlistDocument wishlistDocument = wishlistMongoRepository.save(WishlistDocument.create(wishlist));
        return Wishlist.create(wishlistDocument);
    }

    @Override
    public Optional<Wishlist> findByCustomerId(final String customerId) {
        return wishlistMongoRepository.findByCustomerId(customerId)
                .map(Wishlist::create);
    }
}
