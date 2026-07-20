package com.surely.challenge.adapter.data.product.repoimplementation;

import com.surely.challenge.adapter.data.product.mapper.ProductDataMapper;
import com.surely.challenge.adapter.data.product.repository.GetTopExpensiveProductsRepository;
import com.surely.challenge.product.model.Product;
import com.surely.challenge.product.output.GetTopProductsGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetTopExpensiveProductsRepoImplementation implements GetTopProductsGateway {

    private final GetTopExpensiveProductsRepository getTopExpensiveProductsRepository;

    public GetTopExpensiveProductsRepoImplementation(GetTopExpensiveProductsRepository getTopExpensiveProductsRepository) {
        this.getTopExpensiveProductsRepository = getTopExpensiveProductsRepository;
    }

    @Override
    public List<Product> getTopExpensiveProductsByDocument(String documentNumber, int limit) {
        return getTopExpensiveProductsRepository.findTopExpensiveProductsByDocument(documentNumber, limit)
                .stream()
                .map(ProductDataMapper::dataEntityToCoreMapper)
                .toList();
    }
}
