package com.ticketmaster.api.app.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketmaster.api.app.event.dtos.UploadEventRequestDTO;
import com.ticketmaster.api.app.event.service.EventService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping
    public ResponseEntity<String> uploadEvent(@RequestBody @Valid UploadEventRequestDTO dto) {

        this.eventService.uploadEvent(dto);

        return ResponseEntity.ok().build();
    }
}
