package com.surely.challenge.product.output;

import com.surely.challenge.product.model.Product;
import java.util.List;

public interface GetAllProductsGateway {
    List<Product> findAllActive();
}
