package com.surely.challenge.product.usecase;

import com.surely.challenge.product.input.GetAllProductsInput;
import com.surely.challenge.product.model.Product;
import com.surely.challenge.product.output.GetAllProductsGateway;

import java.util.List;

public class GetAllProductsUseCase implements GetAllProductsInput {

    private final GetAllProductsGateway getAllProductsGateway;

    public GetAllProductsUseCase(GetAllProductsGateway getAllProductsGateway) {
        this.getAllProductsGateway = getAllProductsGateway;
    }

    @Override
    public List<Product> getAllProducts() {
        return getAllProductsGateway.findAllActive();
    }
}
