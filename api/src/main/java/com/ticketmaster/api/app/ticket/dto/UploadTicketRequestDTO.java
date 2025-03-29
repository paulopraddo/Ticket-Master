package com.ticketmaster.api.app.ticket.dto;

import com.ticketmaster.api.domain.ticket.model.TicketType;

public record UploadTicketRequestDTO(TicketType ticketType, Double price, Integer quantity, String eventName) {

}
