package com.surely.challenge.cart.strategy.implementation;

import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.strategy.AbstractPricingStrategy;

import java.math.BigDecimal;

public class SpecialDatePricingStrategy extends AbstractPricingStrategy {

    @Override
    public BigDecimal applyDiscount(Cart cart, BigDecimal total, int totalItems) {

        BigDecimal descuento = new BigDecimal("500");

        if (totalItems > 5) {
            if (total.compareTo(descuento) >= 0) {
                total = total.subtract(descuento);
            } else {
                total = BigDecimal.ZERO;
            }
        }

        return total;
    }
}
