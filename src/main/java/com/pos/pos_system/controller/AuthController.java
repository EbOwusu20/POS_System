
package com.pos.pos_system.controller;

import com.pos.pos_system.dto.AuthRequestDTO;
import com.pos.pos_system.dto.AuthResponseDTO;
import com.pos.pos_system.model.User;
import com.pos.pos_system.security.JwtService;
import com.pos.pos_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService){
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDTO credentials) {
        try {
            String userField = credentials.getUsername() != null ? credentials.getUsername().trim() : "";
            User user = userService.login(userField, credentials.getPassword());
            String role = user.getRole() != null ? user.getRole().name() : "CASHIER";

            String token = jwtService.generateToken(
                    user.getUsername(),
                    Map.of("role", role)
            );

            return ResponseEntity.ok(new AuthResponseDTO(user.getUsername(), role, token));
        } catch (RuntimeException ex) {
            String msg = ex.getMessage();
            if ("User not found".equals(msg) || "Invalid password".equals(msg)) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Map.of("error", "Invalid username or password"));
            }
            throw ex;
        }
    }
}
