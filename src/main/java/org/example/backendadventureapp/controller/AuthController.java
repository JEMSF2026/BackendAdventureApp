package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:63342", "http://4.235.123.111"})
public class AuthController {

    @Autowired
    private AuthService authService;

    // Modtager email og password som JSON body fra frontenden
    // Map<String, String> bruges for at undgå en separat DTO klasse
    @PostMapping("/auth/login")
    public ResponseEntity<Void> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        if (authService.login(email, password)) {
            // 200 OK – frontenden viser dashboardet
            return ResponseEntity.ok().build();
        }
        // 401 Unauthorized – frontenden viser fejlbesked
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}