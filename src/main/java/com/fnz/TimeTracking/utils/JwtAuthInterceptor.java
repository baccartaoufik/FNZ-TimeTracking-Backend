package com.fnz.TimeTracking.utils;

import com.fnz.TimeTracking.model.Utilisateur;
import com.fnz.TimeTracking.repository.UtilisateurRepository;
import com.fnz.TimeTracking.service.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

@Component
public class JwtAuthInterceptor implements HandlerInterceptor {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  UtilisateurRepository utilisateurRepository;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (handler instanceof HandlerMethod) {
      Method method = ((HandlerMethod) handler).getMethod();

      // Check if the method is annotated with @JwtAuth
      if (method.isAnnotationPresent(JwtAuth.class)) {
        // Check if the request contains the bearer header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          return false;
        }

        // Retrieve the Token
        String token = authHeader.substring(7);

        // Extract the email from the token
        String email = jwtUtil.extractUsername(token);

        // Retrieve User Details
        Utilisateur userDetails = utilisateurRepository.findByEmail(email);
        if (userDetails == null) {
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          return false;
        }

        // Validate the token
        if (jwtUtil.validateToken(token, userDetails)) {
          return true;
        } else {
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          return false;
        }
      }
    }
    return true;
  }
}