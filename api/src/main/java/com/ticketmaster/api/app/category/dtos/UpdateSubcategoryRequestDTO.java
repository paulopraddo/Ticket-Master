package com.ticketmaster.api.app.category.dtos;

import com.ticketmaster.api.domain.category.model.EventCategory;

public record UpdateSubcategoryRequestDTO(String name, String newName, String newDescription, EventCategory newEventCategory) {

}
