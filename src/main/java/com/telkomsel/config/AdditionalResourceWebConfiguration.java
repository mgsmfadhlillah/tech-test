package com.telkomsel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String filePath;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/barcode/**")
                .addResourceLocations("file:"+filePath);
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
