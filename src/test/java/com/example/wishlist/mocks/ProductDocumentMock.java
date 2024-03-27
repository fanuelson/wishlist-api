package com.example.wishlist.mocks;

import com.example.wishlist.gateways.db.documents.ProductDocument;

public class ProductDocumentMock {

    public static ProductDocument create(final String productId) {
        final ProductDocument productDocument = new ProductDocument();
        productDocument.setId(productId);
        productDocument.setName("Product" + productId);
        productDocument.setPrice(10.5);
        return productDocument;
    }
}
