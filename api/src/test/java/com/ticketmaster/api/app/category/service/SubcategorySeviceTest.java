package com.ticketmaster.api.app.category.service;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.ticketmaster.api.app.category.dtos.UploadSubcategoryRequestDTO;
import com.ticketmaster.api.domain.category.model.EventCategory;
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
    void testUploadSubategory() {
        UploadSubcategoryRequestDTO dto = new UploadSubcategoryRequestDTO("Test", "TestDescription", EventCategory.TECHNOLOGY);

        subcategorySevice.uploadSubcategory(dto);

        verify(subcategoryRepository, times(1)).save(argThat(subcategory ->
            subcategory.getName().equals(dto.name()) &&
            subcategory.getDescription().equals(dto.description()) &&
            subcategory.getEventCategory() == dto.eventCategory()
        ));
    }
}
