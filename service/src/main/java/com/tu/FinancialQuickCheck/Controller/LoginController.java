package com.tu.FinancialQuickCheck.Controller;

import org.springframework.web.bind.annotation.*;

/**
 * The LoginController manages and processes login requests from the user
 */
@RestController
public class LoginController {

    /**
     * This method is for the login into the Financial Quick Check Application.
     *
     * @param username Your assigned username.
     * @param password Your password related to the username.
     * @param rememberMe If rememberMe is checked, the system will perform automatic login.
     * @return Confirmation that you logged into the application.
     */
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
