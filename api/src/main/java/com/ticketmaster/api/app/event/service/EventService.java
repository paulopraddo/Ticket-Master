package com.ticketmaster.api.app.event.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketmaster.api.app.event.dtos.GetEventResponseDTO;
import com.ticketmaster.api.app.event.dtos.UploadEventRequestDTO;
import com.ticketmaster.api.domain.category.repository.SubcategoryRepository;
import com.ticketmaster.api.domain.event.model.Event;
import com.ticketmaster.api.domain.event.repository.EventRepository;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    SubcategoryRepository subcategoryRepository;

    public void uploadEvent(UploadEventRequestDTO dto) {
        Event model = Event.builder()
        .name(dto.name())
        .description(dto.description())
        .dateTime(dto.dateTime())
        .location(dto.location())
        .subcategory(this.subcategoryRepository.findByName(dto.subcategoryName()))
        .capacity(dto.capacity())
        .isRemote(dto.isRemote())
        .build();

        this.eventRepository.save(model);
    }

    public List<GetEventResponseDTO> getListOfEvents() {
        List<Event> listOfEvents = this.eventRepository.findAll();

        return listOfEvents.stream().map(event -> new GetEventResponseDTO(
            event.getName(), 
            event.getDescription(), 
            event.getDateTime(), 
            event.getLocation(), 
            event.getSubcategory().getName(),
            event.getCapacity(), 
            event.getIsRemote())).collect(Collectors.toList());
    }
}

