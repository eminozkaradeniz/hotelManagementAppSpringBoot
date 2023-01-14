package com.hotelManagementApp.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/showLogin")
    public String showLoginPage() {
        return "login-page";
    }

    // add request mapping for custom /access-denied page
    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }
}
