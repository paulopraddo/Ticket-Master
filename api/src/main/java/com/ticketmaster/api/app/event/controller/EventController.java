package com.ticketmaster.api.app.event.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketmaster.api.app.event.dtos.GetEventResponseDTO;
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

    @GetMapping
    public ResponseEntity<List<GetEventResponseDTO>> getListOfEvents() {

        List<GetEventResponseDTO> listOfDtos = this.eventService.getListOfEvents();

        return ResponseEntity.ok().body(listOfDtos);
    }

    @GetMapping("/{name}")
    public ResponseEntity<GetEventResponseDTO> getEventByName(@PathVariable String name) {

        GetEventResponseDTO dto = this.eventService.getEventByName(name);

        return ResponseEntity.ok().body(dto);
    }
}
