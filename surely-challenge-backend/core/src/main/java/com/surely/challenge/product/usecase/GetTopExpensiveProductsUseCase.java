package com.surely.challenge.product.usecase;

import com.surely.challenge.product.input.GetTopExpensiveProductsInput;
import com.surely.challenge.product.model.Product;
import com.surely.challenge.product.output.GetTopProductsGateway;
import com.surely.challenge.product.exception.ProductException;
import com.surely.challenge.user.exception.UserException;
import com.surely.challenge.user.model.User;
import com.surely.challenge.user.output.GetUserGateway;

import java.util.List;

public class GetTopExpensiveProductsUseCase implements GetTopExpensiveProductsInput {

    private final GetTopProductsGateway getTopProductsGateway;
    private final GetUserGateway getUserGateway;

    public GetTopExpensiveProductsUseCase(GetTopProductsGateway getTopProductsGateway, GetUserGateway getUserGateway) {
        this.getTopProductsGateway = getTopProductsGateway;
        this.getUserGateway = getUserGateway;
    }

    @Override
    public List<Product> getTopProducts(String documentNumber) {
        
        User user = getUserGateway.findByDocument(documentNumber);
        
        if (user == null) {
            throw new UserException("No existe un usuario registrado para el documento: " + documentNumber);
        }

        List<Product> products = getTopProductsGateway.getTopExpensiveProductsByDocument(documentNumber, 4);

        if (products == null || products.isEmpty()) {
            throw new ProductException("El cliente no tiene compras registradas");
        }

        return products;
    }
}
