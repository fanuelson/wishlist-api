package com.example.wishlist.gateways.db.documents;

import com.example.wishlist.domain.Wishlist;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Document("wishlist")
public class WishlistDocument {

    @Id
    private String id;

    @Indexed
    private String customerId;

    private List<ProductDocument> products = Collections.emptyList();

    public static WishlistDocument create(final Wishlist wishlist) {
        final WishlistDocument wishlistDocument = new WishlistDocument();
        wishlistDocument.setId(wishlist.getId());
        wishlistDocument.setCustomerId(wishlist.getCustomerId());
        wishlistDocument.setProducts(wishlist.getProducts().stream().map((ProductDocument::create)).collect(Collectors.toList()));
        return wishlistDocument;
    }
}


