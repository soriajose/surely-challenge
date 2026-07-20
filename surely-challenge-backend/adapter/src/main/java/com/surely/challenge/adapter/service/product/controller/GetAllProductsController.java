package com.surely.challenge.adapter.service.product.controller;

import com.surely.challenge.adapter.service.product.dto.ProductResponseDTO;
import com.surely.challenge.adapter.service.product.mapper.ProductDTOMapper;
import com.surely.challenge.product.input.GetAllProductsInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class GetAllProductsController {

    private final GetAllProductsInput getAllProductsInput;
    public GetAllProductsController(GetAllProductsInput getAllProductsInput) {
        this.getAllProductsInput = getAllProductsInput;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> response = getAllProductsInput.getAllProducts().stream()
                .map(ProductDTOMapper::coreToResponseDtoMapper)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
