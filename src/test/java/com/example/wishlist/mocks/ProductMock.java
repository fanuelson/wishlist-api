package com.example.wishlist.mocks;

import com.example.wishlist.domain.Product;

public class ProductMock {


    public static Product create() {
        return create("1");
    }

    public static Product create(final String productId) {
        return create(productId,"Product" + productId);
    }

    public static Product create(final String productId, final String name) {
        final Product product = new Product();
        product.setId(productId);
        product.setName(name);
        product.setPrice(10.5);
        return product;
    }
}
