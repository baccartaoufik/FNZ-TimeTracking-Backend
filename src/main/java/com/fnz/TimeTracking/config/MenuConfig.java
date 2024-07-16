package com.fnz.TimeTracking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Configuration
@ConfigurationProperties(prefix = "menu")
@Getter
@Setter
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
        private String path;
        private List<String> roles;
        private List<MenuItemConfig> subItems;
    }
}