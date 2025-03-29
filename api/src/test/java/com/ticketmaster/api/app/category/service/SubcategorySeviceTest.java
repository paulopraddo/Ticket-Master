package com.ticketmaster.api.app.category.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.ticketmaster.api.app.category.dtos.GetSubcategoryResponseDTO;
import com.ticketmaster.api.app.category.dtos.UpdateSubcategoryRequestDTO;
import com.ticketmaster.api.app.category.dtos.UploadSubcategoryRequestDTO;
import com.ticketmaster.api.domain.category.model.EventCategory;
import com.ticketmaster.api.domain.category.model.Subcategory;
import com.ticketmaster.api.domain.category.repository.SubcategoryRepository;

public class SubcategorySeviceTest {

    @Mock
    SubcategoryRepository subcategoryRepository;

    @Autowired
    @InjectMocks
    private SubcategorySevice subcategorySevice;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    @DisplayName("Should create subcateroy sucessfuly when everything is OK")
    void testUploadSubategorySucess() {
        UploadSubcategoryRequestDTO dto = new UploadSubcategoryRequestDTO("Test", "TestDescription", EventCategory.TECHNOLOGY);

        subcategorySevice.uploadSubcategory(dto);

        verify(subcategoryRepository, times(1)).save(argThat(subcategory ->
            subcategory.getName().equals(dto.name()) &&
            subcategory.getDescription().equals(dto.description()) &&
            subcategory.getEventCategory() == dto.eventCategory()
        ));
    }

    @Test
    @DisplayName("Should return subcategory DTO when subcategory exists")
    void testGetSubcategorySuccess() {
        Subcategory subcategory = Subcategory.builder()
            .name("Test")
            .description("Test Description")
            .eventCategory(EventCategory.TECHNOLOGY)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        when(subcategoryRepository.findByName("Test")).thenReturn(subcategory);

        GetSubcategoryResponseDTO response = subcategorySevice.getSubcategory("Test");

        assertNotNull(response);
        assertEquals("Test", response.name());
        assertEquals("Test Description", response.description());
        assertEquals(EventCategory.TECHNOLOGY, response.eventCategory());
        assertEquals(subcategory.getCreatedAt(), response.createdAt());
        assertEquals(subcategory.getUpdatedAt(), response.updateAt());

        verify(subcategoryRepository, times(1)).findByName("Test");
    }

    @Test
    @DisplayName("Should throw exception when subcategory does not exist")
    void testGetSubcategoryThrowsExceptionWhenNull() {
        when(subcategoryRepository.findByName("NonExisting")).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            subcategorySevice.getSubcategory("NonExisting");
        });

        assertEquals("Error while trying to find subcategory", exception.getMessage());

        verify(subcategoryRepository, times(1)).findByName("NonExisting");
    }

    @Test
    @DisplayName("Should return a list of subcategory dtos when subcategories exists")
    void testGetListOfSubcategoriesSucess() {
        Subcategory subcategory1 = Subcategory.builder()
            .name("Test1")
            .description("Test Description")
            .eventCategory(EventCategory.TECHNOLOGY)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        Subcategory subcategory2 = Subcategory.builder()
            .name("Test2")
            .description("Test Description")
            .eventCategory(EventCategory.TECHNOLOGY)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        List<Subcategory> subcategories = List.of(subcategory1, subcategory2);

        when(subcategoryRepository.findAll()).thenReturn(subcategories);

        List<GetSubcategoryResponseDTO> response = subcategorySevice.getListOfSubcategories();

        assertNotNull(response);
        assertEquals(2, response.size());

        assertEquals(subcategories.get(0).getName(), response.get(0).name());
        assertEquals(subcategories.get(0).getDescription(), response.get(0).description());
        assertEquals(subcategories.get(0).getEventCategory(), response.get(0).eventCategory());
        assertEquals(subcategories.get(0).getCreatedAt(), response.get(0).createdAt());
        assertEquals(subcategories.get(0).getUpdatedAt(), response.get(0).updateAt());

        assertEquals(subcategories.get(1).getName(), response.get(1).name());
        assertEquals(subcategories.get(1).getDescription(), response.get(1).description());
        assertEquals(subcategories.get(1).getEventCategory(), response.get(1).eventCategory());
        assertEquals(subcategories.get(1).getCreatedAt(), response.get(1).createdAt());
        assertEquals(subcategories.get(1).getUpdatedAt(), response.get(1).updateAt());

        verify(subcategoryRepository, times(1)).findAll();   
    }

    @Test
    @DisplayName("Should throw exception when subcategories is empty")
    void testGetListOfSubcategoriesThrowsExceptionWhenEmpty() {
        List<Subcategory> subcategories = new ArrayList<Subcategory>();

        when(subcategoryRepository.findAll()).thenReturn(subcategories);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            subcategorySevice.getListOfSubcategories();
        });

        assertEquals("Error while trying to find subcategories", exception.getMessage());

        verify(subcategoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should update subcategory successfully when it exists")
    void testUpdateSubcategorySuccess() {
        Subcategory subcategory = Subcategory.builder()
            .name("Tech")
            .description("Tech Events")
            .eventCategory(EventCategory.TECHNOLOGY)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        UpdateSubcategoryRequestDTO dto = new UpdateSubcategoryRequestDTO(
            "Tech", 
            "TestUpdate",
            "New Description", 
            EventCategory.ENTERTAINMENT);

        when(subcategoryRepository.findByName(dto.name())).thenReturn(subcategory);

        subcategorySevice.updateSubcategory(dto);

        assertEquals(dto.newName(), subcategory.getName());
        assertEquals(dto.newDescription(), subcategory.getDescription());
        assertEquals(dto.newEventCategory(), subcategory.getEventCategory());

        verify(subcategoryRepository, times(1)).findByName("Tech");
        verify(subcategoryRepository, times(1)).save(subcategory);
    }

    @Test
    @DisplayName("Should throw exception when subcategory does not exist")
    void testUpdateSubcategoryNotFound() {
        when(subcategoryRepository.findByName("NonExisting")).thenReturn(null);

        UpdateSubcategoryRequestDTO dto = new UpdateSubcategoryRequestDTO(
            "NonExisting", 
            "TestUpdate",
            "New Description", 
            EventCategory.ENTERTAINMENT);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            subcategorySevice.updateSubcategory(dto);
        });

        assertEquals("Error while trying to find subcategory", exception.getMessage());

        verify(subcategoryRepository, times(1)).findByName("NonExisting");
        verify(subcategoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update only provided fields")
    void testUpdateSubcategoryPartialUpdate() {
        Subcategory subcategory = Subcategory.builder()
            .name("Music")
            .description("Music Events")
            .eventCategory(EventCategory.ENTERTAINMENT)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        UpdateSubcategoryRequestDTO dto = new UpdateSubcategoryRequestDTO(
            "Music", 
            null ,
            "Updated Music Events", 
            null);

        when(subcategoryRepository.findByName("Music")).thenReturn(subcategory);

        subcategorySevice.updateSubcategory(dto);

        assertEquals(dto.newDescription(), subcategory.getDescription());
        assertEquals("Music", subcategory.getName());
        assertEquals(EventCategory.ENTERTAINMENT, subcategory.getEventCategory());

        verify(subcategoryRepository, times(1)).findByName("Music");
        verify(subcategoryRepository, times(1)).save(subcategory);
    }


}
