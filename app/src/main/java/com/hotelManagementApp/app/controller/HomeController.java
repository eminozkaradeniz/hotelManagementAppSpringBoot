package com.hotelManagementApp.app.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String showHome(Authentication authentication) {

        // check if any user logged in
        Collection<? extends GrantedAuthority> authorities = null;

        try {
            authorities = authentication.getAuthorities();
        } catch (NullPointerException e) {
            System.out.println("No user logged in");
            return "login-page";
        }

        System.out.println(authentication.getName() + " logged in.");

        return "home";
    }
}
