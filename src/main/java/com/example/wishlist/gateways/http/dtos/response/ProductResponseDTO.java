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

    public static ProductResponseDTO create(Product product) {
        ProductResponseDTO findAllProductsResponseDTO = new ProductResponseDTO();
        findAllProductsResponseDTO.setId(product.getId());
        findAllProductsResponseDTO.setName(product.getName());
        findAllProductsResponseDTO.setPrice(product.getPrice());
        return findAllProductsResponseDTO;
    }
}
