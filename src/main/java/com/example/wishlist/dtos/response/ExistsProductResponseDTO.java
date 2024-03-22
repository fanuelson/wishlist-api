package com.example.wishlist.dtos.response;

import lombok.Data;

@Data
public class ExistsProductResponseDTO {

    private boolean exists;

    private ExistsProductResponseDTO() {}

    public static ExistsProductResponseDTO create(boolean exists) {
        ExistsProductResponseDTO existsProductResponseDTO = new ExistsProductResponseDTO();
        existsProductResponseDTO.setExists(exists);
        return existsProductResponseDTO;
    }
}
