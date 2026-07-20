package com.surely.challenge.adapter.data.product.repoimplementation;

import com.surely.challenge.adapter.data.product.mapper.ProductDataMapper;
import com.surely.challenge.adapter.data.product.repository.ProductRepository;
import com.surely.challenge.product.model.Product;
import com.surely.challenge.product.output.GetProductGateway;
import org.springframework.stereotype.Service;

@Service
public class GetProductRepoImplementation implements GetProductGateway {

    private final ProductRepository productRepository;

    public GetProductRepoImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product findById(Long productId) {
        return productRepository.findByIdAndDeletedAtIsNull(productId)
                .map(ProductDataMapper::dataEntityToCoreMapper)
                .orElse(null);
    }
}
