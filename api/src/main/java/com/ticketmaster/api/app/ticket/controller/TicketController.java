package com.ticketmaster.api.app.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketmaster.api.app.ticket.dto.GetTicketsFromEventResponseDTO;
import com.ticketmaster.api.app.ticket.dto.UpdateTicketRequestDTO;
import com.ticketmaster.api.app.ticket.dto.UploadTicketRequestDTO;
import com.ticketmaster.api.app.ticket.service.TicketService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<String> createTicket(@RequestBody @Valid UploadTicketRequestDTO dto) {

        this.ticketService.uploadTicket(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{eventName}")
    public ResponseEntity<List<GetTicketsFromEventResponseDTO>> getTicketsFromEvent(@PathVariable String eventName) {

        List<GetTicketsFromEventResponseDTO> ticketsList = ticketService.getTicketsFromEvent(eventName);

        return ResponseEntity.ok().body(ticketsList);
    }

    @PutMapping
    public ResponseEntity<String> updateTicket(@RequestBody @Validated UpdateTicketRequestDTO dto) {
        try {
            this.ticketService.updateTicket(dto);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
