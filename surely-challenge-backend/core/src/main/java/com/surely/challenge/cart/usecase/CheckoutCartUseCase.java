package com.surely.challenge.cart.usecase;

import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.input.CheckoutCartInput;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.model.CartStatus;
import com.surely.challenge.cart.output.CreateCartGateway;
import com.surely.challenge.cart.output.GetCartGateway;

import java.time.LocalDate;

public class CheckoutCartUseCase implements CheckoutCartInput {

    private final GetCartGateway getCartGateway;
    private final CreateCartGateway createCartGateway;

    public CheckoutCartUseCase(GetCartGateway getCartGateway, CreateCartGateway createCartGateway) {
        this.getCartGateway = getCartGateway;
        this.createCartGateway = createCartGateway;
    }

    @Override
    public void checkoutCart(Long cartId) {
        Cart cart = getCartGateway.findById(cartId);

        if (cart == null) {
            throw new CartException("No existe un cart con el ID: " + cartId);
        }

        if (cart.getDeletedAt() != null || cart.getStatus() == CartStatus.CANCELED) {
            throw new CartException("El cart con ID: " + cartId + " se encuentra cancelado o eliminado");
        }

        if (cart.getStatus() == CartStatus.CLOSED) {
            throw new CartException("El cart con ID: " + cartId + " ya se encuentra finalizado");
        }

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new CartException("No puedes finalizar la compra de un cart vacío");
        }

        cart.setStatus(CartStatus.CLOSED);
        cart.setUpdatedAt(LocalDate.now());
        cart.setUpdatedBy(cart.getUser().getEmail());

        createCartGateway.saveCart(cart);
    }
}
