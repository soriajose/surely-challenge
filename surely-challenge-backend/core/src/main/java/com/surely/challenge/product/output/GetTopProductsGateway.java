package com.surely.challenge.product.output;

import com.surely.challenge.product.model.Product;

import java.util.List;

public interface GetTopProductsGateway {
    List<Product> getTopExpensiveProductsByDocument(String documentNumber, int limit);
}
