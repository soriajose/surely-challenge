package com.surely.challenge.adapter.service.product.config;

import com.surely.challenge.product.input.GetAllProductsInput;
import com.surely.challenge.product.input.GetTopExpensiveProductsInput;
import com.surely.challenge.product.output.GetAllProductsGateway;
import com.surely.challenge.product.output.GetTopProductsGateway;
import com.surely.challenge.product.usecase.GetAllProductsUseCase;
import com.surely.challenge.product.usecase.GetTopExpensiveProductsUseCase;
import com.surely.challenge.user.output.GetUserGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductBeanConfig {

    @Bean
    public GetTopExpensiveProductsInput getTopExpensiveProductsInput(GetTopProductsGateway getTopProductsGateway, GetUserGateway getUserGateway) {
        return new GetTopExpensiveProductsUseCase(getTopProductsGateway, getUserGateway);
    }

    @Bean
    public GetAllProductsInput getAllProductsInput(GetAllProductsGateway getAllProductsGateway) {
        return new GetAllProductsUseCase(getAllProductsGateway);
    }


}
