package br.com.sandes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("RESTful API with Java 21 and Spring Bott 3")
                        .version("v1")
                        .description("A API do manage people")
                        .termsOfService("https://pub.sandes.com.br/minha-api")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://pub.sandes.com.br/minha-api")));
    }
}
