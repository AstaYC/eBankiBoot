package com.example.ebankify.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {
    @GetMapping("")
    public ResponseEntity<?> index(){
        return ResponseEntity.ok("welcom");
    }
}
