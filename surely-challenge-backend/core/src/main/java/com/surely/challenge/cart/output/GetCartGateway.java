package com.surely.challenge.cart.output;

import com.surely.challenge.cart.model.Cart;

public interface GetCartGateway {

    Cart findById(Long id);

}
