package com.example.ebankify.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/users")
    public String manageUsers() {
        return "Admin: Managing Users";
    }

    @GetMapping("/accounts")
    public String manageAccounts() {
        return "Admin: Managing Accounts";
    }
}
