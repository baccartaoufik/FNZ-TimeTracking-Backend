package com.fnz.TimeTracking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "menu")
public class MenuConfig {
    private List<MenuItemConfig> items;

    public List<MenuItemConfig> getItems() {
        return items;
    }

    public void setItems(List<MenuItemConfig> items) {
        this.items = items;
    }

    @Getter
    @Setter
    public static class MenuItemConfig {
        private String name;
        private String icon;
        private boolean active;
        private List<String> roles;



    }
}