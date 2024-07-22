package com.fnz.TimeTracking.model;


import com.fnz.TimeTracking.config.MenuConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuItem {
    private String name;
    private String icon;
    private boolean active;
    private List<MenuConfig.MenuItemConfig> subItems;

    public MenuItem(String name, String icon, boolean active, List<MenuConfig.MenuItemConfig> subItems) {
        this.name = name;
        this.icon = icon;
        this.active = active;
        this.subItems = subItems;
    }


}