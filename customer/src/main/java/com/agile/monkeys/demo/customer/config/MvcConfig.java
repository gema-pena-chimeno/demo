package com.agile.monkeys.demo.customer.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Getter
    @Value("${customer.image.image_folder}")
    private String imageFolder;

    @Getter
    @Value("${customer.image.url_path}")
    private String urlPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        log.info("Mapping URL path {}/** to serve images located in the folder {}", urlPath, imageFolder);
        registry.addResourceHandler(urlPath + "/**")
                .addResourceLocations("file:"+ imageFolder);
    }
}


