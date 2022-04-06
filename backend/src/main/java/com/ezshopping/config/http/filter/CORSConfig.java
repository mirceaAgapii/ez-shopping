package com.ezshopping.config.http.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .exposedHeaders("access_token","refresh_token", "userId")
                .allowedHeaders("Origin", "Content-Type", "Accept",
                                "Authorization", "Access-Control-Allow-Origin",
                                "Access-Control-Request-Method", "Access-Control-Request-Headers",
                                "X-Request-With")
                .allowedOrigins("http://localhost:4200");  //TODO: change the URL for the prod URL when we deploy - use different properties files for diff env
        //TODO: or use annotation @Profile

    }
}
