package com.example.wishlist.mocks;

import com.example.wishlist.gateways.db.documents.ProductDocument;
import com.example.wishlist.gateways.db.documents.WishlistDocument;

import java.util.List;

public class WishlistDocumentMock {


    public static WishlistDocument create(final List<ProductDocument> products) {
        return create("1", products);
    }

    public static WishlistDocument create(final String customerId, final List<ProductDocument> products) {
        final WishlistDocument wishlistDocument = new WishlistDocument();
        wishlistDocument.setCustomerId(customerId);
        wishlistDocument.setProducts(products);
        return wishlistDocument;
    }
}
