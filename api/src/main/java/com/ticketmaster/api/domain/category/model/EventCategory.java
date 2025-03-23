package com.ticketmaster.api.domain.category.model;

public enum EventCategory {
    ENTERTAINMENT("ENTERTAINMENT"),
    TECHNOLOGY("TECHNOLOGY"),
    EDUCATION("EDUCATION"),
    BUSINESS("BUSINESS"),
    HEALTH("HEALTH"),
    SPORTS("SPORTS");

    private final String value;

    EventCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
