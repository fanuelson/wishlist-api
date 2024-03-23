package com.example.wishlist.gateways.http.dtos.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductExistsResponseDTO {

    private boolean exists;

    public static ProductExistsResponseDTO create(boolean exists) {
        ProductExistsResponseDTO existsProductResponseDTO = new ProductExistsResponseDTO();
        existsProductResponseDTO.setExists(exists);
        return existsProductResponseDTO;
    }
}
