package com.hotelManagementApp.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The LoginController class handles request related to user authentication and login-related views.
 * It provides endpoints to display the login page and access-denied page.
 */
@Controller
public class LoginController {

    /**
     * Handles GET requests for the "/showLogin" endpoint.
     *
     * @return The view name for displaying the login page.
     */
    @GetMapping("/showLogin")
    public String showLoginPage() {
        return "login-page";
    }

    /**
     * Handles GET requests for the "/access-denied" endpoint.
     *
     * @return The view name for displaying the access-denied page.
     */
    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }
}
