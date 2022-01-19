package com.tu.FinancialQuickCheck.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class HelloWorldController {

    @GetMapping("/helloworld")
    public String index() {
        return "Hello World!";
    }
}
