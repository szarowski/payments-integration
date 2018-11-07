package com.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The Payments Application class
 */
@EnableFeignClients
@SpringBootApplication
public class PaymentsIntegrationApp extends SpringApplication {

    public static void main(final String[] args) {
        SpringApplication.run(PaymentsIntegrationApp.class, args);
    }
}