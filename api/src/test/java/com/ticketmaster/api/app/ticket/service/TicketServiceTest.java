package com.ticketmaster.api.app.ticket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ticketmaster.api.app.ticket.dto.GetTicketsFromEventResponseDTO;
import com.ticketmaster.api.app.ticket.dto.UpdateTicketRequestDTO;
import com.ticketmaster.api.app.ticket.dto.UploadTicketRequestDTO;
import com.ticketmaster.api.domain.event.model.Event;
import com.ticketmaster.api.domain.event.repository.EventRepository;
import com.ticketmaster.api.domain.ticket.model.Ticket;
import com.ticketmaster.api.domain.ticket.model.TicketType;
import com.ticketmaster.api.domain.ticket.repository.TicketRepository;

import jakarta.persistence.EntityNotFoundException;

public class TicketServiceTest {

    @Mock
    TicketRepository ticketRepository;

    @Mock
    EventRepository eventRepository;

    @InjectMocks
    TicketService ticketService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should return a list of GetTicketsFromEventResponseDTO sucessfuly")
    void testGetTicketsFromEventSucess() {
        Event event = new Event();
        event.setId("1");
        event.setName("TestEvent");

        Ticket ticket1 = new Ticket(
            "1",
            TicketType.VIP, 
            30.00, 
            300, 
            event);

        Ticket ticket2 = new Ticket(
            "2",
            TicketType.CAMAROTE, 
            50.00, 
            300, 
            event);

        List<Ticket> tickets = List.of(ticket1, ticket2);

        when(eventRepository.findByName("TestEvent")).thenReturn(event);
        when(ticketRepository.findByEventId(event.getId())).thenReturn(tickets);
        
        List<GetTicketsFromEventResponseDTO> responseDTOs = ticketService.getTicketsFromEvent("TestEvent");

        assertNotNull(responseDTOs);
        assertEquals(tickets.size(), responseDTOs.size());
        assertEquals(ticket1.getTicketType(), responseDTOs.get(0).ticketType());
        assertEquals(ticket1.getPrice(), responseDTOs.get(0).price());
        assertEquals(ticket2.getQuanty(), responseDTOs.get(1).quanty());
        assertEquals(ticket2.getEvent().getName(), responseDTOs.get(1).eventName());

        verify(eventRepository, times(1)).findByName("TestEvent");
        verify(ticketRepository, times(1)).findByEventId(event.getId());
    }

    @Test
    @DisplayName("Should return a RuntimeException when eventName is not found")
    void testGetTicketsFromEventThrowsException() {

        String eventName = "NotFound";

        when(eventRepository.findByName(eventName)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> ticketService.getTicketsFromEvent(eventName)
        );

        assertEquals("Error while trying to find event", exception.getMessage());

        verify(eventRepository, times(1)).findByName(eventName);
    }

    @Test
    void shouldThrowExceptionWhenTicketAlreadyExists() {
        Event event = new Event();
        event.setName("TestEvent");

        UploadTicketRequestDTO dto = new UploadTicketRequestDTO(TicketType.VIP, 100.00, 100, "TestEvent");

        Ticket existingTicket = new Ticket();
        existingTicket.setTicketType(TicketType.VIP);

        when(eventRepository.findByName(dto.eventName())).thenReturn(event);
        when(ticketRepository.findByEventName(dto.eventName())).thenReturn(List.of(existingTicket));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticketService.uploadTicket(dto);
        });

        assertEquals("A ticket of type 'VIP' already exists for this event.", exception.getMessage());
    }

   @Test
    void shouldUpdateTicketSuccessfully() {
        UpdateTicketRequestDTO dto = new UpdateTicketRequestDTO();
        dto.setId("1");
        dto.setNewEventName("New Event");
        dto.setNewPrice(99.99);
        dto.setNewTicketType(TicketType.VIP);
        dto.setNewQuanty(50);

        Event mockEvent = new Event();
        mockEvent.setName("New Event");

        Ticket mockTicket = new Ticket();
        mockTicket.setId("1");
        mockTicket.setPrice(99.99);
        mockTicket.setTicketType(TicketType.CAMAROTE);
        mockTicket.setQuanty(100);
        mockTicket.setEvent(new Event());

        when(ticketRepository.findById("1")).thenReturn(Optional.of(mockTicket));
        when(eventRepository.findByName("New Event")).thenReturn(mockEvent);

        ticketService.updateTicket(dto);

        assertEquals(TicketType.VIP, mockTicket.getTicketType());
        assertEquals(99.99, mockTicket.getPrice());
        assertEquals(50, mockTicket.getQuanty());
        assertEquals(mockEvent, mockTicket.getEvent());

        verify(ticketRepository).save(mockTicket);
    }

    @Test
    void shouldThrowExceptionWhenTicketNotFound() {
        UpdateTicketRequestDTO dto = new UpdateTicketRequestDTO();
        dto.setId("1");

        when(ticketRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            ticketService.updateTicket(dto);
        });

        assertEquals("Ticket not found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEventNotFound() {
        UpdateTicketRequestDTO dto = new UpdateTicketRequestDTO();
        dto.setId("1");
        dto.setNewEventName("Nonexistent Event");

        Ticket mockTicket = new Ticket();
        mockTicket.setId("1");

        when(ticketRepository.findById("1")).thenReturn(Optional.of(mockTicket));
        when(eventRepository.findByName("Nonexistent Event")).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            ticketService.updateTicket(dto);
        });

        assertEquals("Error while trying to find event", exception.getMessage());
    }
}
