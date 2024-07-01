package com.fnz.TimeTracking.utils;

import com.fnz.TimeTracking.model.Utilisateur;
import com.fnz.TimeTracking.repository.UtilisateurRepository;
import com.fnz.TimeTracking.service.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

@Component
public class JwtAuthInterceptor implements HandlerInterceptor
{

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  UtilisateurRepository utilisateurRepository;

  @Override
  public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {
    if (handler instanceof HandlerMethod ) {
      Method method = ((HandlerMethod) handler).getMethod();
      if (method.isAnnotationPresent(com.fnz.TimeTracking.utils.JwtAuth.class)) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header.");
          return false;
        }

        String token = authHeader.substring(7);
        try {
          // Assume Utilisateur class has a method to retrieve user details by email
          String email = jwtUtil.extractUsername(token);
          Utilisateur userDetails = utilisateurRepository.findByEmail( email );// fetch user details using email
          if (!jwtUtil.validateToken(token, userDetails)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
            return false;
          }
        } catch (Exception e) {
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
          return false;
        }
      }
    }
    return true;
  }
}
