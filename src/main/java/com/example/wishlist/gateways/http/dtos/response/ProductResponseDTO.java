package com.example.wishlist.gateways.http.dtos.response;

import com.example.wishlist.domain.Product;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponseDTO {

    private String id;
    private String name;
    private Double price;

    public static ProductResponseDTO create(final Product product) {
        final ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setPrice(product.getPrice());
        return productResponseDTO;
    }
}
