package com.surely.challenge.cart.input;

import com.surely.challenge.cart.model.Cart;

public interface GetCartStateInput {
    Cart getCartState(Long cartId);
}
