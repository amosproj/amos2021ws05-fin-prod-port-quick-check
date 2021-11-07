package com.tu.FinancialQuickCheck.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/helloworld")
    public String index() {
        return "Hello World!";
    }
}
