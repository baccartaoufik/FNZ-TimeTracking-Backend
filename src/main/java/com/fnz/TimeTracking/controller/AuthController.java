package com.fnz.TimeTracking.controller;
import com.fnz.TimeTracking.model.Utilisateur;
import com.fnz.TimeTracking.repository.UtilisateurRepository;
import com.fnz.TimeTracking.service.AuthService;
import com.fnz.TimeTracking.service.JwtUtil;
import com.fnz.TimeTracking.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
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
        try {
            ResponseEntity<String> response = authService.validateFace(file);

            if (response.getStatusCode() == HttpStatus.OK) {
                String email = response.getBody();
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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during login process");
        }
    }
}