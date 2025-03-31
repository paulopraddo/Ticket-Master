package com.ticketmaster.api.app.ticket.dto;

import com.ticketmaster.api.domain.ticket.model.TicketType;

public record GetTicketsFromEventResponseDTO(TicketType ticketType, Double price, Integer quanty, String eventName) {

}
