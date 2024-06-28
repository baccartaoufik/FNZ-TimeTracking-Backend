package com.fnz.TimeTracking.controller;

import com.fnz.TimeTracking.model.Utilisateur;
import com.fnz.TimeTracking.service.AuthService;
import com.fnz.TimeTracking.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController
{

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login( @RequestBody Map<String, String> request ) {
        String image = request.get("image");

        //Calls the AuthService to validate the face and get user details
        Utilisateur userDetails = authService.validateFace(image);

        // If user is validated, generate a JWT token
        if (userDetails != null) {

            String token = jwtUtil.generateToken(userDetails);

            // Return the token in the response body with HTTP 200 OK status
            return ResponseEntity.ok( Collections.singletonMap("token", token ) );
        } else {

            // If validation fails, return an HTTP 401 Unauthorized status with a message
            return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).body("Invalid face recognition" );
        }
    }
}