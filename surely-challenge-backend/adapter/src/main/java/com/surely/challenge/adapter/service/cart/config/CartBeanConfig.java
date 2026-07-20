package com.surely.challenge.adapter.service.cart.config;

import com.surely.challenge.cart.input.AddProductToCartInput;
import com.surely.challenge.cart.input.CreateCartInput;
import com.surely.challenge.cart.input.DeleteCartInput;
import com.surely.challenge.cart.input.GetCartStateInput;
import com.surely.challenge.cart.input.RemoveProductFromCartInput;
import com.surely.challenge.cart.output.CreateCartGateway;
import com.surely.challenge.cart.output.GetCartGateway;
import com.surely.challenge.cart.output.GetPromotionalDateGateway;
import com.surely.challenge.cart.output.RemoveProductFromCartGateway;
import com.surely.challenge.cart.usecase.AddProductToCartUseCase;
import com.surely.challenge.cart.usecase.CreateCartUseCase;
import com.surely.challenge.cart.usecase.DeleteCartUseCase;
import com.surely.challenge.cart.usecase.GetCartStateUseCase;
import com.surely.challenge.cart.usecase.RemoveProductFromCartUseCase;
import com.surely.challenge.product.output.GetProductGateway;
import com.surely.challenge.user.output.GetUserGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartBeanConfig {

    @Bean
    public CreateCartInput createCartInput(CreateCartGateway createCartGateway, GetUserGateway getUserGateway, GetPromotionalDateGateway getPromotionalDateGateway) {
        return new CreateCartUseCase(createCartGateway, getUserGateway, getPromotionalDateGateway);
    }

    @Bean
    public DeleteCartInput deleteCartInput(GetCartGateway getCartGateway, CreateCartGateway createCartGateway) {
        return new DeleteCartUseCase(getCartGateway, createCartGateway);
    }

    @Bean
    public AddProductToCartInput addProductToCartInput(CreateCartGateway createCartGateway, GetProductGateway getProductGateway, GetCartGateway getCartGateway) {
        return new AddProductToCartUseCase(createCartGateway, getProductGateway, getCartGateway);
    }

    @Bean
    public RemoveProductFromCartInput removeProductFromCartInput(GetCartGateway getCartGateway, RemoveProductFromCartGateway removeProductFromCartGateway) {
        return new RemoveProductFromCartUseCase(getCartGateway, removeProductFromCartGateway);
    }

    @Bean
    public GetCartStateInput getCartStateInput(GetCartGateway getCartGateway) {
        return new GetCartStateUseCase(getCartGateway);
    }

    @Bean
    public com.surely.challenge.cart.input.CheckoutCartInput checkoutCartInput(GetCartGateway getCartGateway, CreateCartGateway createCartGateway) {
        return new com.surely.challenge.cart.usecase.CheckoutCartUseCase(getCartGateway, createCartGateway);
    }
}
