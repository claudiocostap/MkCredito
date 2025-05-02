package com.makarios.mkcredito.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configura o CORS para permitir requisições de http://localhost:4200
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")  // Substitua pela URL do seu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Permite os métodos HTTP
                .allowedHeaders("*")  // Permite qualquer cabeçalho
                .allowCredentials(true);  // Permite envio de cookies ou credenciais
    }
}
