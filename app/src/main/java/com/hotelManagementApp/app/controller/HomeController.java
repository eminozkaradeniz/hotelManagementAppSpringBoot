package com.hotelManagementApp.app.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/home")
    public String showHome(Authentication authentication) {

        // check if any user logged in
        String username = "";
        try {
            username = authentication.getName();
        } catch (NullPointerException e) {
            System.out.println("No user logged in");
            return "login-page";
        }

        System.out.println(username + " logged in.");

        return "home";
    }
}
