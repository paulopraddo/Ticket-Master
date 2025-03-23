package com.ticketmaster.api.app.category.dtos;

import com.ticketmaster.api.domain.category.model.EventCategory;

public record UploadSubcategoryRequestDTO(String name, String description, EventCategory eventCategory) {

}
