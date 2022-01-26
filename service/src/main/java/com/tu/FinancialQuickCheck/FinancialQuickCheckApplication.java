package com.tu.FinancialQuickCheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This is our main method, witch is starting the Financial Quick Check Application
 */
@SpringBootApplication
public class FinancialQuickCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialQuickCheckApplication.class, args);
    }

}
