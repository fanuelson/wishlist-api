package com.example.wishlist.entities;

import com.example.wishlist.dtos.request.ProductDTO;
import lombok.Data;

@Data
public class Product {

    private String id;
    private String name;
    private Double price;

    private Product() {

    }

    public static Product create(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        return product;
    }
}
