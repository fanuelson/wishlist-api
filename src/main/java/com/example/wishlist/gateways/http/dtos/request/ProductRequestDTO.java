package com.example.wishlist.gateways.http.dtos.request;

import com.example.wishlist.gateways.db.documents.ProductDocument;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRequestDTO {

    private String id;
    private String name;
    private Double price;

    public static ProductRequestDTO create(ProductDocument product) {
        ProductRequestDTO productDTO = new ProductRequestDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }
}
