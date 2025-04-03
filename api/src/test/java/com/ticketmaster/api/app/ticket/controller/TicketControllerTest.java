package com.ticketmaster.api.app.ticket.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ticketmaster.api.app.ticket.dto.GetTicketsFromEventResponseDTO;
import com.ticketmaster.api.app.ticket.dto.UpdateTicketRequestDTO;
import com.ticketmaster.api.app.ticket.service.TicketService;
import com.ticketmaster.api.domain.ticket.model.TicketType;

public class TicketControllerTest {

    @Mock
    TicketService ticketService;

    @InjectMocks
    TicketController ticketController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should return a ResponseEntity<List<GetTicketsFromEventResponseDTO>> with status OK")
    void testGetTicketsFromEvent() {
        
        String eventName = "TestEvent";

        GetTicketsFromEventResponseDTO dto1 = new GetTicketsFromEventResponseDTO(TicketType.CAMAROTE, 30.00, 300, eventName);
        GetTicketsFromEventResponseDTO dto2 = new GetTicketsFromEventResponseDTO(TicketType.VIP, 50.00, 300, eventName);

        List<GetTicketsFromEventResponseDTO> listOfTickets = List.of(dto1, dto2);

        when(ticketService.getTicketsFromEvent(eventName)).thenReturn(listOfTickets);

        ResponseEntity<List<GetTicketsFromEventResponseDTO>> response = ticketController.getTicketsFromEvent(eventName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(listOfTickets, response.getBody());

        verify(ticketService, times(1)).getTicketsFromEvent(eventName);
    }

    @Test
    void shouldUpdateTicketSucessfuly() {
        UpdateTicketRequestDTO dto = new UpdateTicketRequestDTO();
        dto.setId("1");
        
        ResponseEntity<String> responseEntity = ticketController.updateTicket(dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(ticketService, times(1)).updateTicket(dto);
    }

    @Test
    void shouldThrowException() {
        UpdateTicketRequestDTO dto = new UpdateTicketRequestDTO();

        doThrow(new RuntimeException()).when(ticketService).updateTicket(dto);

        ResponseEntity<String> responseEntity = ticketController.updateTicket(dto);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}
