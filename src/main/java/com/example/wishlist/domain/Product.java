package com.example.wishlist.domain;

import com.example.wishlist.gateways.db.documents.ProductDocument;
import com.example.wishlist.gateways.http.dtos.request.ProductRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"id"})
public class Product {

    private String id;
    private String name;
    private Double price;

    public static Product create(final ProductDocument productDocument) {
        final Product product = new Product();
        product.setId(productDocument.getId());
        product.setName(productDocument.getName());
        product.setPrice(productDocument.getPrice());
        return product;
    }

    public static Product create(final ProductRequestDTO productDTO) {
        final Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        return product;
    }

    public static Product create(final String productId) {
        final Product product = new Product();
        product.setId(productId);
        return product;
    }
}
