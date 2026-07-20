package com.surely.challenge.cart.factory;

import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.strategy.PricingStrategy;
import com.surely.challenge.cart.strategy.implementation.NormalPricingStrategy;
import com.surely.challenge.cart.strategy.implementation.SpecialDatePricingStrategy;
import com.surely.challenge.cart.strategy.implementation.VipPricingStrategy;
import com.surely.challenge.user.model.User;

public class CartFactory {

    public static Cart createCartFactory(User user, boolean isPromotionalDate) {

        PricingStrategy strategy;

        if (user != null && user.isVip()) {
            strategy = new VipPricingStrategy();
        } else if (isPromotionalDate) {
            strategy = new SpecialDatePricingStrategy();
        } else {
            strategy = new NormalPricingStrategy();
        }

        return Cart.factory(user, strategy);
    }

}
