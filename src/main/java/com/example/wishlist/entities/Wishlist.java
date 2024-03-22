package com.example.wishlist.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Wishlist {

    @Id
    private WishlistId id;

    private Product product;

    private Wishlist() {
    }

    public static Wishlist create(WishlistId id, Product product) {
        Wishlist wishlist = new Wishlist();
        wishlist.setId(id);
        wishlist.setProduct(product);
        return wishlist;
    }
}
