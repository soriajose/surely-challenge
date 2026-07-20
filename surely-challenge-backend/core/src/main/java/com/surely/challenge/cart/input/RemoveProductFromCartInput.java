package com.surely.challenge.cart.input;

import com.surely.challenge.cart.model.Cart;

public interface RemoveProductFromCartInput {
    Cart removeProduct(Long productId, Long cartId);
}
