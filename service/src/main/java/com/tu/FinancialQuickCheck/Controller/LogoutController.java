package com.tu.FinancialQuickCheck.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The LogoutController manages and processes logout requests from the user
 */
@RestController
public class LogoutController {

    /**
     * This method is for logging out of the Financial Quick Check Application.
     *
     * @return Confirmation that you logged out of the application.
     */
    // TODO: change to post method     
    @GetMapping("/logout")
    public String logoutUser() {
        return "You are trying to logout!";
    }
}
