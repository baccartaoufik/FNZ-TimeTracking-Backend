package com.fnz.TimeTracking.controller;

import com.fnz.TimeTracking.model.MenuItem;
import com.fnz.TimeTracking.model.Utilisateur;
import com.fnz.TimeTracking.repository.UtilisateurRepository;
import com.fnz.TimeTracking.service.JwtUtil;
import com.fnz.TimeTracking.service.MenuService;
import com.fnz.TimeTracking.utils.JwtAuth;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/config")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UtilisateurRepository utilisateurRepository;


    // TODO: Change when Role Managment is implemented
    @JwtAuth
    @GetMapping("/menu")
    public ResponseEntity<List<MenuItem>> getMenu(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        String email = jwtUtil.extractUsername(token);
        Utilisateur user = utilisateurRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        List<MenuItem> menu = menuService.getMenuForRole(user.getRole().getNomRole());
        return ResponseEntity.ok(menu);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}