package com.example.wishlist.gateways.http.dtos.request;

import com.example.wishlist.gateways.db.documents.ProductDocument;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotBlank
    private String id;
    @NotBlank
    private String name;
    @NotNull
    @Positive
    private Double price;

    public static ProductRequestDTO create(final ProductDocument product) {
        final ProductRequestDTO productDTO = new ProductRequestDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }
}
