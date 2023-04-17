package com.xuanluan.mc.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Xuan Luan
 * @createdAt 2/19/2023
 */
@EnableJpaRepositories(basePackages = {"com.xuanluan.mc.repository.sequence", "com.xuanluan.mc.product"})
@EntityScan(basePackages = {"com.xuanluan.mc.domain.entity", "com.xuanluan.mc.product"})
@ComponentScan(basePackages = {"com.xuanluan.mc.service", "com.xuanluan.mc.product"})
@SpringBootApplication
@EnableEurekaClient
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
