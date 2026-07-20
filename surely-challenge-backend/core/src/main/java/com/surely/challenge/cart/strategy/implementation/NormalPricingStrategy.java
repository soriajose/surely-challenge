package com.surely.challenge.cart.strategy.implementation;

import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.strategy.AbstractPricingStrategy;

import java.math.BigDecimal;

public class NormalPricingStrategy extends AbstractPricingStrategy {

    @Override
    public BigDecimal applyDiscount(Cart cart, BigDecimal total, int totalItems) {

        return total;
    }
}
