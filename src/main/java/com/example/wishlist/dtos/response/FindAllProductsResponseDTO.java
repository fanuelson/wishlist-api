package com.example.wishlist.dtos.response;

import com.example.wishlist.dtos.request.ProductDTO;
import com.example.wishlist.entities.Product;
import lombok.Data;

@Data
public class FindAllProductsResponseDTO {

    private String id;
    private String name;
    private Double price;

    private FindAllProductsResponseDTO() {

    }

    public static FindAllProductsResponseDTO create(Product product) {
        FindAllProductsResponseDTO findAllProductsResponseDTO = new FindAllProductsResponseDTO();
        findAllProductsResponseDTO.setId(product.getId());
        findAllProductsResponseDTO.setName(product.getName());
        findAllProductsResponseDTO.setPrice(product.getPrice());
        return findAllProductsResponseDTO;
    }
}
