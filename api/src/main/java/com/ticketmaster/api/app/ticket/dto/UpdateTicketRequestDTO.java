package com.ticketmaster.api.app.ticket.dto;

import com.ticketmaster.api.domain.ticket.model.TicketType;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateTicketRequestDTO {
    
    private String id;
    private TicketType newTicketType;
    @Min(value = 0)
    private Double newPrice;
    @Min(value = 0)
    private Integer newQuanty;
    private String newEventName;
}
