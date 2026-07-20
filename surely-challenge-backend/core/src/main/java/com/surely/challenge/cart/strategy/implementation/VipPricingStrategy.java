package com.surely.challenge.cart.strategy.implementation;

import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.strategy.AbstractPricingStrategy;
import com.surely.challenge.cartitem.model.CartItem;

import java.math.BigDecimal;
import java.util.Comparator;

public class VipPricingStrategy extends AbstractPricingStrategy {

    @Override
    public BigDecimal applyDiscount(Cart cart, BigDecimal total, int totalItems) {

        BigDecimal descuento = new BigDecimal("700");

        CartItem cartItemMinPricing = cart.getItems().stream()
                .filter(item -> item.getDeletedAt() == null)
                .min(Comparator.comparing(item -> item.getProduct().getPrice()))
                .orElse(null);

        if (totalItems > 5) {
            if (cartItemMinPricing != null) {
                total = total.subtract(cartItemMinPricing.getProduct().getPrice());
            }

            if (total.compareTo(descuento) >= 0) {
                total = total.subtract(descuento);
            } else {
                total = BigDecimal.ZERO;
            }
        }

        return total;
    }
}
