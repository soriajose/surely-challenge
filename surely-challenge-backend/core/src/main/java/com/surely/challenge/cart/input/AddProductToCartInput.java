package com.surely.challenge.cart.input;

import com.surely.challenge.cart.model.Cart;

public interface AddProductToCartInput {

    Cart addProduct(Long productId, Long cartId);

}
