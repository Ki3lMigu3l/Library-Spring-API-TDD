package com.github.ki3lmigu3l.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("library-api")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library API: Api de gerenciamento de emprestimos de livros")
                        .version("1.0")
                        .contact(new Contact()
                                .email("ki3lmigu3l@gmail.com")
                                .url("https://github.com/Ki3lMigu3l")
                                .name("Ezequiel Miguel")));
    }
}
