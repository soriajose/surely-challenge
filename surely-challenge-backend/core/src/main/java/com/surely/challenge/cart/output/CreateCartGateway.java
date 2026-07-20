package com.surely.challenge.cart.output;

import com.surely.challenge.cart.model.Cart;

public interface CreateCartGateway {

    Cart saveCart(Cart cart);

}
