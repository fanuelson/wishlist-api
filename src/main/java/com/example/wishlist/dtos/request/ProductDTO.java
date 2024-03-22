package com.example.wishlist.dtos.request;

import com.example.wishlist.entities.Product;
import lombok.Data;

@Data
public class ProductDTO {

    private String id;
    private String name;
    private Double price;

    private ProductDTO() {

    }

    public static ProductDTO create(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }
}
