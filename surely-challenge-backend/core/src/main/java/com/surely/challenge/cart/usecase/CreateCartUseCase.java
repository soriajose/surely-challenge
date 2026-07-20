package com.surely.challenge.cart.usecase;

import com.surely.challenge.cart.factory.CartFactory;
import com.surely.challenge.cart.input.CreateCartInput;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.output.CreateCartGateway;
import com.surely.challenge.cart.output.GetPromotionalDateGateway;
import com.surely.challenge.user.exception.UserException;
import com.surely.challenge.user.model.User;
import com.surely.challenge.user.output.GetUserGateway;

import java.time.LocalDate;

public class CreateCartUseCase implements CreateCartInput {

    private final CreateCartGateway createCartGateway;
    private final GetUserGateway getUserGateway;
    private final GetPromotionalDateGateway getPromotionalDateGateway;

    public CreateCartUseCase(CreateCartGateway createCartGateway, GetUserGateway getUserGateway, GetPromotionalDateGateway getPromotionalDateGateway) {
        this.createCartGateway = createCartGateway;
        this.getUserGateway = getUserGateway;
        this.getPromotionalDateGateway = getPromotionalDateGateway;
    }

    @Override
    public Long createCart(String documento) {
        // despues agregar validaciones

        User user = getUserGateway.findByDocument(documento);

        if (user == null) {
            throw new UserException("No existe un usuario registrado para el documento: " + documento);
        }

        boolean isPromotionalDate = getPromotionalDateGateway.isPromotionalDate(LocalDate.now());

        Cart cart = CartFactory.createCartFactory(user, isPromotionalDate);

        return createCartGateway.saveCart(cart).getId();
    }
}
