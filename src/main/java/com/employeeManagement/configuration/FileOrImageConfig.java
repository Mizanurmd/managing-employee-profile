package com.employeeManagement.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileOrImageConfig implements WebMvcConfigurer {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Convert backslashes to forward slashes for Windows paths
        String resourceLocation = "file:///" + uploadDir.replace("\\", "/") + "/";
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(resourceLocation);

        System.out.println("Serving files from: " + resourceLocation);
    }
}
