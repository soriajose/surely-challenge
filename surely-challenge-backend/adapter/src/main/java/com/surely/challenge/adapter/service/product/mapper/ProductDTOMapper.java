package com.surely.challenge.adapter.service.product.mapper;

import com.surely.challenge.adapter.service.product.dto.ProductResponseDTO;
import com.surely.challenge.product.model.Product;

public class ProductDTOMapper {
    
    private ProductDTOMapper() {}

    public static ProductResponseDTO coreToResponseDtoMapper(Product product) {
        if (product == null) return null;

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        return dto;
    }
}
