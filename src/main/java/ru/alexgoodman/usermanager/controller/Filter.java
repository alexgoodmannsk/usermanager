package ru.alexgoodman.usermanager.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Filter {
    private String filterName;
    private boolean enabled;

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
