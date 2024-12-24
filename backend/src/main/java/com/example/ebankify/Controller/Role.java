package com.example.ebankify.Controller;

import com.example.ebankify.Util.JwtUtil;
import com.example.ebankify.Controller.vm.User.Request.CreateUserRequest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class Role {

    private JwtUtil jwtUtil;

    public Role(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/getRole")
    public String getRole(@RequestHeader("Authorization") String authorizationHeader) {
        // Check if the header is null or doesn't start with "Bearer "
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        String token = authorizationHeader.replace("Bearer ", "").trim();

        // Additional validation
        if (token.isEmpty()) {
            throw new IllegalArgumentException("Token is empty");
        }

        return "Roles: " + jwtUtil.getRole(token);
    }
}
