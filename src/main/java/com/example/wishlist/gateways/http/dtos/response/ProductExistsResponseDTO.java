package com.example.wishlist.gateways.http.dtos.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductExistsResponseDTO {

    private boolean exists;

    public static ProductExistsResponseDTO create(final boolean exists) {
        final ProductExistsResponseDTO productExistsResponseDTO = new ProductExistsResponseDTO();
        productExistsResponseDTO.setExists(exists);
        return productExistsResponseDTO;
    }
}
