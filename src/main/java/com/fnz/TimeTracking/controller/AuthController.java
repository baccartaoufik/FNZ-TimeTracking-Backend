package com.fnz.TimeTracking.controller;
import com.fnz.TimeTracking.model.Utilisateur;
import com.fnz.TimeTracking.service.AuthService;
import com.fnz.TimeTracking.service.JwtUtil;
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

    //private RestTemplate restTemplate;
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    RestTemplate restTemplate = new RestTemplate();

    private final String FLASK_API_URL= "http://localhost:5000"; // Adjust this to your Flask API URL

    @Autowired
    public AuthController( AuthService authService, JwtUtil jwtUtil) {
        //this.restTemplate = restTemplate;
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam("file") MultipartFile file,
                                      @RequestParam("userId") String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());
        body.add("user_id", userId);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                FLASK_API_URL + "/register",
                requestEntity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return ResponseEntity.ok().body("User registered successfully");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("file") MultipartFile file) {
        try {
            ResponseEntity<String> response = authService.validateFace(file);

            if (response.getStatusCode() == HttpStatus.OK) {
                String email = response.getBody();
                if (email != null && !email.isEmpty()) {
                    try {
                        Utilisateur user = authService.loadUserByUsername(email);
                        String token = jwtUtil.generateToken(user);
                        return ResponseEntity.ok(Collections.singletonMap("token", token));
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error processing user authentication");
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