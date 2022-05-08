package com.ezshopping.config.cloud;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("${cloud_name}")
    private String cloud_name;
    @Value("${api_key}")
    private String api_key;
    @Value("${api_secret}")
    private String api_secret;

    @Bean
    public Cloudinary cloudinary() {
        Map<String , String> configValues = new HashMap<>();
        configValues.put("cloud_name", cloud_name);
        configValues.put("api_key", api_key);
        configValues.put("api_secret", api_secret);
        return new Cloudinary(configValues);
    }
}