package com.tu.FinancialQuickCheck.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    // TODO: change to post method     
    @GetMapping("/logout")
    public String logoutUser() {
        return "You are trying to logout!";
    }
}
