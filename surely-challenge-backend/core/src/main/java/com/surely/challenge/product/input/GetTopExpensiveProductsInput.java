package com.surely.challenge.product.input;

import com.surely.challenge.product.model.Product;

import java.util.List;

public interface GetTopExpensiveProductsInput {
    List<Product> getTopProducts(String documentNumber);
}
