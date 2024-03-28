package com.example.wishlist.mocks;

import com.example.wishlist.domain.Product;
import com.example.wishlist.domain.Wishlist;

import java.util.ArrayList;
import java.util.List;

public class WishlistMock {


    public static Wishlist create() {
        return create("1", new ArrayList<>());
    }

    public static Wishlist create(final List<Product> products) {
        return create("1", products);
    }

    public static Wishlist create(final String customerId, final List<Product> products) {
        final Wishlist wishlist = Wishlist.create(customerId);
        wishlist.setProducts(products);
        return wishlist;
    }
}
