package com.example.wishlist.domain;

import com.example.wishlist.gateways.db.documents.WishlistDocument;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class Wishlist {

    private String id;
    private String customerId;
    private List<Product> products;

    public static Wishlist create(WishlistDocument wishlistDocument) {
        return Wishlist
                .builder()
                .id(wishlistDocument.getId())
                .customerId(wishlistDocument.getCustomerId())
                .products(wishlistDocument.getProducts().stream().map(Product::create).collect(Collectors.toList()))
                .build();
    }

    public static Wishlist create(String customerId) {
        return Wishlist
                .builder()
                .customerId(customerId)
                .products(new ArrayList<>())
                .build();
    }
}
