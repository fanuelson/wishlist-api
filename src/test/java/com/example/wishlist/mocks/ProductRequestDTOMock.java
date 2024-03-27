package com.example.wishlist.mocks;

import com.example.wishlist.gateways.http.dtos.request.ProductRequestDTO;

public class ProductRequestDTOMock {

    public static ProductRequestDTO create() {
        return create("1");
    }

    public static ProductRequestDTO create(final String productId) {
        final ProductRequestDTO productRequest = new ProductRequestDTO();
        productRequest.setId(productId);
        productRequest.setName("Product" + productId);
        productRequest.setPrice(10.5);
        return productRequest;
    }
}
