package com.surely.challenge.adapter.service.product.controller;

import com.surely.challenge.adapter.service.product.dto.ProductResponseDTO;
import com.surely.challenge.adapter.service.product.mapper.ProductDTOMapper;
import com.surely.challenge.product.input.GetTopExpensiveProductsInput;
import com.surely.challenge.product.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class GetTopExpensiveProductsController {

    private final GetTopExpensiveProductsInput getTopExpensiveProductsInput;

    public GetTopExpensiveProductsController(GetTopExpensiveProductsInput getTopExpensiveProductsInput) {
        this.getTopExpensiveProductsInput = getTopExpensiveProductsInput;
    }


    @GetMapping("/top-expensive/{document}")
    public ResponseEntity<?> getTopExpensiveProducts(@PathVariable String document) {

        List<Product> products = this.getTopExpensiveProductsInput.getTopProducts(document);

        // despues de obtener la lista de productos, los mapeo al responsedto
        List<ProductResponseDTO> response = products.stream()
                .map(ProductDTOMapper::coreToResponseDtoMapper)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
