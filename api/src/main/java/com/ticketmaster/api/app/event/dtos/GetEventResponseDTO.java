package com.ticketmaster.api.app.event.dtos;

import java.time.LocalDateTime;

public record GetEventResponseDTO(String name, String description, LocalDateTime dateTime, String location, String subcategoryName, Integer capacity, Boolean isRemote) {

}
