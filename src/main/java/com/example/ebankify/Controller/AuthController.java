package com.example.ebankify.Controller;

import com.example.ebankify.DTO.LoginRequest; // Import the LoginRequest DTO
import com.example.ebankify.DTO.UserDTO;
import com.example.ebankify.Service.UserService;
import com.example.ebankify.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();  // Use the LoginRequest DTO for email
        String password = loginRequest.getPassword();  // Use the LoginRequest DTO for password

        // Authenticate user
        UserDTO user = userService.getUserByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        // Generate token
        String token = jwtUtil.generateToken(user.getId(), user.getRole().toString());
        return ResponseEntity.ok(token);  // Return the token
    }
}
