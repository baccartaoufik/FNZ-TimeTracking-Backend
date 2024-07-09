package com.fnz.TimeTracking.controller;

import com.fnz.TimeTracking.model.MenuItem;
import com.fnz.TimeTracking.service.MenuService;
import com.fnz.TimeTracking.utils.JwtAuth;
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


    // TODO: Change when Role Managment is implemented
    @JwtAuth
    @GetMapping("/menu")
    public ResponseEntity<List<MenuItem>> getMenu() {
        List<MenuItem> menu = menuService.getMenuForRole("user");
        return ResponseEntity.ok(menu);
    }
}