package com.xuanluan.mc.product.config;

import com.xuanluan.mc.utils.PropertyReaderUtils;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Xuan Luan
 * @createdAt 1/14/2023
 */
@Configuration
public class WebMvcConfig {
    @PostConstruct
    void initProfile() {
        PropertyReaderUtils.getProfileProperties();
    }

    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${swagger.url}")
    private String swaggerUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url(swaggerUrl + contextPath))
                .info(new Info().title("Product Serivce").version("v1.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
