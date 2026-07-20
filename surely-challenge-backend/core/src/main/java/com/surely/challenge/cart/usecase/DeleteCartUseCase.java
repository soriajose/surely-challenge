package com.surely.challenge.cart.usecase;

import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.input.DeleteCartInput;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.model.CartStatus;
import com.surely.challenge.cart.output.CreateCartGateway;
import com.surely.challenge.cart.output.GetCartGateway;

import java.time.LocalDate;

public class DeleteCartUseCase implements DeleteCartInput {

    private final GetCartGateway getCartGateway;
    private final CreateCartGateway createCartGateway;

    public DeleteCartUseCase(GetCartGateway getCartGateway, CreateCartGateway createCartGateway) {
        this.getCartGateway = getCartGateway;
        this.createCartGateway = createCartGateway;
    }

    @Override
    public void deleteCart(Long cartId) {
        Cart cart = getCartGateway.findById(cartId);

        if (cart == null) {
            throw new CartException("No existe un cart con el ID: " + cartId);
        }

        if (cart.getDeletedAt() != null || cart.getStatus() != CartStatus.OPEN) {
            throw new CartException("El cart con ID: " + cartId + " ya no se puede modificar porque no está abierto.");
        }

        cart.setDeletedAt(LocalDate.now());
        cart.setDeletedBy(cart.getUser().getEmail());
        cart.setStatus(CartStatus.CANCELED);

        if (cart.getItems() != null) {
            cart.getItems().forEach(item -> {
                if (item.getDeletedAt() == null) {
                    item.setDeletedAt(LocalDate.now());
                    item.setDeletedBy(cart.getUser().getEmail());
                }
            });
        }

        createCartGateway.saveCart(cart);
    }
}
