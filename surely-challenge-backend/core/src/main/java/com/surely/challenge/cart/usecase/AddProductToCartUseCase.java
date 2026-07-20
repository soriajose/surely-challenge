package com.surely.challenge.cart.usecase;

import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.input.AddProductToCartInput;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.model.CartStatus;
import com.surely.challenge.cart.output.CreateCartGateway;
import com.surely.challenge.cart.output.GetCartGateway;
import com.surely.challenge.product.exception.ProductException;
import com.surely.challenge.product.model.Product;
import com.surely.challenge.product.output.GetProductGateway;

public class AddProductToCartUseCase implements AddProductToCartInput {

    private final CreateCartGateway createCartGateway;
    private final GetProductGateway getProductGateway;
    private final GetCartGateway getCartGateway;

    public AddProductToCartUseCase(CreateCartGateway createCartGateway, GetProductGateway getProductGateway, GetCartGateway getCartGateway) {
        this.createCartGateway = createCartGateway;
        this.getCartGateway = getCartGateway;
        this.getProductGateway = getProductGateway;
    }

    @Override
    public Cart addProduct(Long productId, Long cartId) {

        Cart cart = getCartGateway.findById(cartId);

        if (cart == null) {
            throw new CartException("No existe un cart con el ID: " + cartId);
        }

        if (cart.getDeletedAt() != null || cart.getStatus() != CartStatus.OPEN) {
            throw new CartException("El cart con ID: " + cartId + " ya no se puede modificar porque no está abierto.");
        }

        Product product = getProductGateway.findById(productId);

        if (product == null) {
            throw new ProductException("No existe un producto con el ID: " + productId);
        }

        cart.addProduct(product);

        return createCartGateway.saveCart(cart);
    }
}
