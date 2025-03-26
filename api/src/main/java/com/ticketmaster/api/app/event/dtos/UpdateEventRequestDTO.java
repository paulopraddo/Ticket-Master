package com.ticketmaster.api.app.event.dtos;

import java.time.LocalDateTime;

public record UpdateEventRequestDTO(String name, String newName, String newDescription, LocalDateTime newDateTime, String newLocation, String newSubcategoryName, Integer newCapacity, Boolean isRemote) {

}
