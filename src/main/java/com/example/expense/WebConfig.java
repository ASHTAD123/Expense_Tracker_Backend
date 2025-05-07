package com.example.expense;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        		registry.addMapping("/**")
        		.allowedOrigins("https://symphonious-otter-07cb0e.netlify.app", "https://dev--symphonious-otter-07cb0e.netlify.app")
        		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .allowedHeaders("Authorization", "Content-Type")
         		.exposedHeaders("Set-Cookie");     
        
         
    }
}
