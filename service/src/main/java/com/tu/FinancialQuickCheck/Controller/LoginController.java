package com.tu.FinancialQuickCheck.Controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    // TODO: change to post method     
    @GetMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password,
                            @RequestParam String rememberMe) {

        return "You are trying to login to the Financial Quick Check Application. " +
                "Your Login information: \n username = " + username +
                "\n password = " + password +
                "\n stay logged in = " + rememberMe;
    }
}
