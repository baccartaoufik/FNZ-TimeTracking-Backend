package com.fnz.TimeTracking.service;

import com.fnz.TimeTracking.config.MenuConfig;
import com.fnz.TimeTracking.model.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuConfig menuConfig;

    public List<MenuItem> getMenuForRole(String role) {
        List<MenuItem> menu = new ArrayList<>();

        for (MenuConfig.MenuItemConfig itemConfig : menuConfig.getItems()) {
            if (itemConfig.getRoles().contains(role.toUpperCase())) {
                menu.add(new MenuItem(itemConfig.getName(), itemConfig.getIcon(), itemConfig.isActive()));
            }
        }

        return menu;
    }
}