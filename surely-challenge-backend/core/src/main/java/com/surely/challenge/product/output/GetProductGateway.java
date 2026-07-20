package com.surely.challenge.product.output;

import com.surely.challenge.product.model.Product;

public interface GetProductGateway {

    Product findById(Long id);

}
