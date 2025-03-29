package com.ticketmaster.api.app.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketmaster.api.app.ticket.dto.UploadTicketRequestDTO;
import com.ticketmaster.api.domain.event.model.Event;
import com.ticketmaster.api.domain.event.repository.EventRepository;
import com.ticketmaster.api.domain.ticket.model.Ticket;
import com.ticketmaster.api.domain.ticket.repository.TicketRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    EventRepository eventRepository;

    public void uploadTicket(UploadTicketRequestDTO dto) {

        Event event = this.eventRepository.findByName(dto.eventName());

        Ticket ticket = new Ticket(
            dto.ticketType(), 
            dto.price(), 
            dto.quantity(), 
            event
        );

        this.ticketRepository.save(ticket);
    }
}
