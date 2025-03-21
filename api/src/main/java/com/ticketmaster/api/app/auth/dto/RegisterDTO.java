package com.ticketmaster.api.app.auth.dto;

import com.ticketmaster.api.domain.auth.model.UserRole;

public record RegisterDTO(String username, String email, String password, UserRole role) {

}
