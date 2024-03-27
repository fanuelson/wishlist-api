package com.example.wishlist.gateways.db.documents;

import com.example.wishlist.domain.Product;
import lombok.Data;

@Data
public class ProductDocument {

    private String id;
    private String name;
    private Double price;

    public static ProductDocument create(final Product product) {
        final ProductDocument productDocument = new ProductDocument();
        productDocument.setId(product.getId());
        productDocument.setName(product.getName());
        productDocument.setPrice(product.getPrice());
        return productDocument;
    }
}
