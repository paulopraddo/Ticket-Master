package com.ticketmaster.api.app.event.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ticketmaster.api.app.event.dtos.GetEventResponseDTO;
import com.ticketmaster.api.app.event.dtos.UpdateEventRequestDTO;
import com.ticketmaster.api.app.event.dtos.UploadEventRequestDTO;
import com.ticketmaster.api.domain.category.model.Subcategory;
import com.ticketmaster.api.domain.category.repository.SubcategoryRepository;
import com.ticketmaster.api.domain.event.model.Event;
import com.ticketmaster.api.domain.event.repository.EventRepository;

public class EventServiceTest {

    @Mock
    EventRepository eventRepository;

    @Mock
    SubcategoryRepository subcategoryRepository;

    @InjectMocks
    EventService eventService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should upload event sucessfuly")
    void testUploadEventSucess() {
        UploadEventRequestDTO dto = new UploadEventRequestDTO(
            "TestEvent", 
            "TestDescription", 
            LocalDateTime.now(), 
            "TestLocation", 
            "TestSubcategory", 
            10, 
            true
        );

        Subcategory subcategory = new Subcategory();
        subcategory.setName("TestSubcategory");

        when(subcategoryRepository.findByName("TestSubcategory")).thenReturn(subcategory);

        eventService.uploadEvent(dto);

        verify(eventRepository, times(1)).save(argThat(event ->
            event.getName().equals(dto.name()) &&
            event.getDescription().equals(dto.description()) &&
            event.getDateTime().equals(dto.dateTime()) &&
            event.getLocation().equals(dto.location()) &&
            event.getSubcategory().getName().equals(dto.subcategoryName()) &&
            event.getCapacity().equals(dto.capacity()) &&
            event.getIsRemote().equals(dto.isRemote())
        ));
    }

    @Test
    void testGetEventByNameSucess() {
        Subcategory subcategory = new Subcategory();
        subcategory.setName("TestSubcategory");

        Event event = new Event(
            "1", 
            "TestEvent", 
            "TestDescription", 
            LocalDateTime.now(), 
            "TestLocation", 
            subcategory, 
            3, 
            true);    

        when(eventRepository.findByName("TestEvent")).thenReturn(event);

        GetEventResponseDTO responseDTO = eventService.getEventByName("TestEvent");

        assertNotNull(responseDTO);
        assertEquals(event.getName(), responseDTO.name());
        assertEquals(event.getDescription(), responseDTO.description());
        assertEquals(event.getDateTime(), responseDTO.dateTime());
        assertEquals(event.getLocation(), responseDTO.location());
        assertEquals(event.getSubcategory().getName(), responseDTO.subcategoryName());
        assertEquals(event.getCapacity(), responseDTO.capacity());
        assertEquals(event.getIsRemote(), responseDTO.isRemote());


        verify(eventRepository, times(1)).findByName("TestEvent");
    }

    @Test
    void testUpdateEventSucess() {
        Subcategory subcategory = new Subcategory();
        subcategory.setName("TestSubcategory");

        Subcategory subcategoryUpdate = new Subcategory();
        subcategoryUpdate.setName("TestUpdateSubcategory");

        Event event = new Event(
            "1", 
            "TestEvent", 
            "TestDescription", 
            LocalDateTime.now(), 
            "TestLocation", 
            subcategory, 
            3, 
            true);

        UpdateEventRequestDTO dto = new UpdateEventRequestDTO(
            "TestEvent", 
            "TestNewName", 
            "TestNewDescription", 
            LocalDateTime.now(), 
            "TestNewLocation", 
            "TestUpdateSubcategory", 
            4, 
            false);
        
        when(eventRepository.findByName("TestEvent")).thenReturn(event);
        when(subcategoryRepository.findByName("TestUpdateSubcategory")).thenReturn(subcategoryUpdate);

        eventService.updateEvent(dto);

        assertEquals(dto.newName(), event.getName());
        assertEquals(dto.newDescription(), event.getDescription());
        assertEquals(dto.newDateTime(), event.getDateTime());
        assertEquals(dto.newLocation(), event.getLocation());
        assertEquals(dto.newSubcategoryName(), event.getSubcategory().getName());
        assertEquals(dto.newCapacity(), event.getCapacity());
        assertEquals(dto.isRemote(), event.getIsRemote());

        verify(eventRepository, times(1)).save(event);
        verify(subcategoryRepository, times(1)).findByName("TestUpdateSubcategory");
    }
}
