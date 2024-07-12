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
import java.util.Arrays;

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

      if (method.isAnnotationPresent(JwtAuth.class)) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          return false;
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);
        Utilisateur userDetails = utilisateurRepository.findByEmail(email);

        if (userDetails == null) {
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          return false;
        }
        if (jwtUtil.validateToken(token, userDetails)) {
          JwtAuth jwtAuth = method.getAnnotation(JwtAuth.class);
          String[] allowedRoles = jwtAuth.roles();

          if (allowedRoles.length == 0 || Arrays.asList(allowedRoles).contains(userDetails.getRole().getNomRole())) {
            return true;
          } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
          }
        } else {
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          return false;
        }
      }
    }
    return true;
  }
}