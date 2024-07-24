package com.fnz.TimeTracking.controller;

import com.fnz.TimeTracking.dto.ValidateFaceResponse;
import com.fnz.TimeTracking.model.Utilisateur;
import com.fnz.TimeTracking.repository.UtilisateurRepository;
import com.fnz.TimeTracking.service.AuthService;
import com.fnz.TimeTracking.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://172.16.4.17")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public AuthController(AuthService authService, JwtUtil jwtUtil, UtilisateurRepository utilisateurRepository) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("file") MultipartFile file) {
        ResponseEntity<ValidateFaceResponse> response = authService.validateFace(file);

        if (response.getStatusCode() == HttpStatus.OK) {
            String email = Objects.requireNonNull(response.getBody()).getEmail();
            if (email != null && !email.isEmpty()) {
                Utilisateur user = utilisateurRepository.findByEmail(email);
                if (user != null) {
                    String token = jwtUtil.generateToken(user);
                    return ResponseEntity.ok(Collections.singletonMap("token", token));
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid response from face recognition service");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid face recognition");
        }
    }
}
