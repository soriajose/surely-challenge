package com.surely.challenge.cart.usecase;

import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.input.GetCartStateInput;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.output.GetCartGateway;

public class GetCartStateUseCase implements GetCartStateInput {

    private final GetCartGateway getCartGateway;

    public GetCartStateUseCase(GetCartGateway getCartGateway) {
        this.getCartGateway = getCartGateway;
    }

    @Override
    public Cart getCartState(Long cartId) {

        Cart cart = getCartGateway.findById(cartId);

        if (cart == null) {
            throw new CartException("No existe un cart con el ID: " + cartId);
        }

        if (cart.getDeletedAt() != null) {
            throw new CartException("El cart con ID: " + cartId + " se encuentra eliminado");
        }

        return cart;
    }
}
