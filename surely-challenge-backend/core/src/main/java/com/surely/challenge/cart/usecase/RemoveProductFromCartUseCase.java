package com.surely.challenge.cart.usecase;

import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.input.RemoveProductFromCartInput;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.model.CartStatus;
import com.surely.challenge.cart.output.CreateCartGateway;
import com.surely.challenge.cart.output.GetCartGateway;
import com.surely.challenge.cart.output.RemoveProductFromCartGateway;

public class RemoveProductFromCartUseCase implements RemoveProductFromCartInput {

    private final GetCartGateway getCartGateway;
    private final RemoveProductFromCartGateway removeProductFromCartGateway;

    public RemoveProductFromCartUseCase(GetCartGateway getCartGateway, RemoveProductFromCartGateway removeProductFromCartGateway) {
        this.getCartGateway = getCartGateway;
        this.removeProductFromCartGateway = removeProductFromCartGateway;
    }

    @Override
    public Cart removeProduct(Long productId, Long cartId) {

        Cart cart = getCartGateway.findById(cartId);

        if (cart == null) {
            throw new CartException("No existe un cart con el ID: " + cartId);
        }

        if (cart.getDeletedAt() != null || cart.getStatus() != CartStatus.OPEN) {
            throw new CartException("El cart con ID: " + cartId + " ya no se puede modificar porque no está abierto.");
        }

        cart.removeProduct(productId);

        return removeProductFromCartGateway.removeProduct(cart);
    }
}
