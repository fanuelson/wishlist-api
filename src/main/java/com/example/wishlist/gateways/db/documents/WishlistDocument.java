package com.example.wishlist.gateways.db.documents;

import com.example.wishlist.domain.Wishlist;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@Document("wishlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistDocument {

    @Id
    private String id;

    @Indexed
    private String customerId;

    private List<ProductDocument> products;

    public static WishlistDocument create(Wishlist wishlist) {
        WishlistDocument wishlistDocument = new WishlistDocument();
        wishlistDocument.setId(wishlist.getId());
        wishlistDocument.setCustomerId(wishlist.getCustomerId());
        List<ProductDocument> products = wishlist.getProducts().stream().map((ProductDocument::create)).collect(Collectors.toList());
        wishlistDocument.setProducts(products);
        return wishlistDocument;
    }
}
