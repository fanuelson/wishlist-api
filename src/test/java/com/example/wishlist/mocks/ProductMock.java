package com.example.wishlist.mocks;

import com.example.wishlist.domain.Product;

public class ProductMock {

    public static Product create(final String productId) {
        final Product product = new Product();
        product.setId(productId);
        product.setName("Product" + productId);
        product.setPrice(10.5);
        return product;
    }
}
