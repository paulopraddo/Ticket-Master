package com.ticketmaster.api.app.ticket.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketmaster.api.app.ticket.dto.GetTicketsFromEventResponseDTO;
import com.ticketmaster.api.app.ticket.dto.UpdateTicketRequestDTO;
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

        if(event == null) {
            throw new RuntimeException("Error while trying to find event");
        }        

        List<Ticket> tickets = this.ticketRepository.findByEventName(dto.eventName());

        boolean ticketExtists = tickets.stream()
                .anyMatch(ticket -> ticket.getTicketType().equals(dto.ticketType()));

        if(ticketExtists) {
            throw new RuntimeException("A ticket of type '" + dto.ticketType() + "' already exists for this event.");
        }
        
        Ticket ticket = new Ticket(
            dto.ticketType(), 
            dto.price(), 
            dto.quantity(), 
            event
        );

        this.ticketRepository.save(ticket);
    }

    public List<GetTicketsFromEventResponseDTO> getTicketsFromEvent(String eventName) {

        Event event = this.eventRepository.findByName(eventName);

        if(event == null) {
            throw new RuntimeException("Error while trying to find event");
        }

        List<Ticket> listOfTickets = this.ticketRepository.findByEventId(event.getId());

        return listOfTickets.stream().map(ticket -> new GetTicketsFromEventResponseDTO(
                ticket.getTicketType(), 
                ticket.getPrice(), 
                ticket.getQuanty(), 
                ticket.getEvent().getName()))
            .collect(Collectors.toList());
    }

    public void updateTicket(UpdateTicketRequestDTO dto) {

        Event event = eventRepository.findByName(dto.getNewEventName());

        if(event == null) {
            throw new RuntimeException("Error while trying to find event");
        }

        Ticket ticket = ticketRepository.findById(dto.getId())
            .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if(dto.getNewTicketType() != null) ticket.setTicketType(dto.getNewTicketType());
        if(dto.getNewPrice() != null) ticket.setPrice(dto.getNewPrice());
        if(dto.getNewQuanty() != null) ticket.setQuanty(dto.getNewQuanty());
        if(event != null) ticket.setEvent(event);

        this.ticketRepository.save(ticket);
    }

    public void deleteTicket(String ticketId) {
        this.ticketRepository.deleteById(ticketId);
    }
}
