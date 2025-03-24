package com.ticketmaster.api.app.category.dtos;

import java.time.LocalDateTime;

import com.ticketmaster.api.domain.category.model.EventCategory;

import lombok.Builder;

@Builder
public record GetSubcategoryResponseDTO(String name, String description, EventCategory eventCategory, LocalDateTime createdAt, LocalDateTime updateAt) {

}
