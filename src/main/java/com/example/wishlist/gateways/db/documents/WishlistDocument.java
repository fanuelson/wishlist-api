package com.example.wishlist.gateways.db.documents;

import com.example.wishlist.domain.Wishlist;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("wishlist")
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
        List<ProductDocument> products = wishlist.getProducts().stream().map((ProductDocument::create)).toList();
        wishlistDocument.setProducts(products);
        return wishlistDocument;
    }
}
