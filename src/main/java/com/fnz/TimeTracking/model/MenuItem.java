package com.fnz.TimeTracking.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItem {
    private String name;
    private String icon;
    private boolean active;

    public MenuItem(String name, String icon, boolean active) {
        this.name = name;
        this.icon = icon;
        this.active = active;
    }


}