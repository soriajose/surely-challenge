package com.surely.challenge.adapter.data.product.repoimplementation;

import com.surely.challenge.adapter.data.product.mapper.ProductDataMapper;
import com.surely.challenge.adapter.data.product.repository.ProductRepository;
import com.surely.challenge.product.model.Product;
import com.surely.challenge.product.output.GetAllProductsGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllProductsRepoImplementation implements GetAllProductsGateway {

    private final ProductRepository productRepository;
    public GetAllProductsRepoImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllActive() {
        return productRepository.findAllByDeletedAtIsNull().stream()
                .map(ProductDataMapper::dataEntityToCoreMapper)
                .collect(Collectors.toList());
    }
}
