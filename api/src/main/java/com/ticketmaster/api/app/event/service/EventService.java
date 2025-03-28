package com.ticketmaster.api.app.event.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketmaster.api.app.event.dtos.GetEventResponseDTO;
import com.ticketmaster.api.app.event.dtos.UpdateEventRequestDTO;
import com.ticketmaster.api.app.event.dtos.UploadEventRequestDTO;
import com.ticketmaster.api.domain.category.model.Subcategory;
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

    public GetEventResponseDTO getEventByName(String name) {
        Event model = this.eventRepository.findByName(name);

        if(model == null) {
            throw new RuntimeException("Error while tryng to find event");
        }

        return new GetEventResponseDTO(
            model.getName(), 
            model.getDescription(), 
            model.getDateTime(), 
            model.getLocation(), 
            model.getSubcategory().getName(),
            model.getCapacity(), 
            model.getIsRemote());
    }

    public void updateEvent(UpdateEventRequestDTO dto) {
        Event model = this.eventRepository.findByName(dto.name());

        System.out.println("Evento: " + model);

        if(model == null) {
            throw new RuntimeException("Error while trying to find event");
        }

        Subcategory newSubcategory = this.subcategoryRepository.findByName(dto.newSubcategoryName());

        if(dto.newName() != null) model.setName(dto.newName());
        if(dto.newDescription() != null) model.setDescription(dto.newDescription());
        if(dto.newDateTime() != null) model.setDateTime(dto.newDateTime());
        if(dto.newLocation() != null) model.setLocation(dto.newLocation());
        if(newSubcategory != null) model.setSubcategory(newSubcategory);
        if(dto.newCapacity() != null) model.setCapacity(dto.newCapacity());
        if(dto.isRemote() != null) model.setIsRemote(dto.isRemote());
        
        this.eventRepository.save(model);
    }

    public void deleteEvent(String name) {
        Event model = this.eventRepository.findByName(name);

        if(model == null) {
            throw new RuntimeException("Error while trying to find event");
        }

        this.eventRepository.delete(model);
    }
}

