package com.hotelManagementApp.app.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The HomeController class handles requests related to the application's home page.
 * It checks if a user is logged in and redirects to the home page or the login page accordingly.
 */
@Controller
public class HomeController {

    /**
     * Handles GET requests for the "/home" endpoint.
     *
     * @param authentication An authentication object representing the user's authentication status.
     * @return The view name to render based on the user's authentication status.
     */
    @GetMapping("/home")
    public String showHome(Authentication authentication) {

        // Check if any user is logged in
        String username = "";
        try {
            username = authentication.getName();
        } catch (NullPointerException e) {
            System.out.println("No user logged in");
            return "login-page"; // Redirect to the login page if no user is logged in.
        }

        // Print the username of the logged-in user
        System.out.println(username + " logged in.");

        return "home";
    }
}
