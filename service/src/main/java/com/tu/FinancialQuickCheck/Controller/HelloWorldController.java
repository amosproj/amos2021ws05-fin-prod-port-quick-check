package com.tu.FinancialQuickCheck.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This Controller is for testing the spring boot application with a Hello World example.
 */
@RestController
public class HelloWorldController {

    @GetMapping("/helloworld")
    public String index() {
        return "Hello World!";
    }
}
