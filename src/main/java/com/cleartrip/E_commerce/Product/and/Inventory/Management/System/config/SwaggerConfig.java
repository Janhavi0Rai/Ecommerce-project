
package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("E-commerce API")
                        .description("API documentation for E-commerce system")
                        .version("1.0"));
    }
}
