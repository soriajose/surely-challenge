package com.surely.challenge.cart.strategy;

import com.surely.challenge.cart.model.Cart;

import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculateTotal(Cart cart);
}
