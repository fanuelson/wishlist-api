package com.example.wishlist.domain;

import com.example.wishlist.gateways.http.dtos.request.ProductRequestDTO;
import com.example.wishlist.gateways.db.documents.ProductDocument;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Product {

    private String id;
    private String name;
    private Double price;

    public static Product create(ProductDocument productDocument) {
        Product product = new Product();
        product.setId(productDocument.getId());
        product.setName(productDocument.getName());
        product.setPrice(productDocument.getPrice());
        return product;
    }

    public static Product create(ProductRequestDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        return product;
    }

    public boolean equalsId(final String productId) {
        return id.equals(productId);
    }
}
