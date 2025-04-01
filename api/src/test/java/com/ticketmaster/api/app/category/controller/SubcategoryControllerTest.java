package com.ticketmaster.api.app.category.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.ticketmaster.api.app.category.dtos.GetSubcategoryResponseDTO;
import com.ticketmaster.api.app.category.dtos.UpdateSubcategoryRequestDTO;
import com.ticketmaster.api.app.category.dtos.UploadSubcategoryRequestDTO;
import com.ticketmaster.api.app.category.service.SubcategorySevice;
import com.ticketmaster.api.domain.category.model.EventCategory;

@ExtendWith(MockitoExtension.class)
public class SubcategoryControllerTest {

    @InjectMocks
    SubcategoryController subcategoryController;

    @Mock
    SubcategorySevice subcategoryService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should create subcategory successfully")
    void testUploadSubcategorySucess() throws Exception {
        UploadSubcategoryRequestDTO dto = new UploadSubcategoryRequestDTO("Test", "Test Description", EventCategory.ENTERTAINMENT);

        ResponseEntity<String> response = subcategoryController.uploadSubcategory(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(subcategoryService, times(1)).uploadSubcategory(dto);
    }
    
    @Test
    @DisplayName("Should return error 500 if service throwns an execption")
    void testUploadSubcategoryThrowsException() {
        UploadSubcategoryRequestDTO dto = new UploadSubcategoryRequestDTO("Test", "Test Description", EventCategory.ENTERTAINMENT);

        doThrow(new RuntimeException("Service error")).when(subcategoryService).uploadSubcategory(dto);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        subcategoryController.uploadSubcategory(dto);
        });

        assertEquals("Service error", exception.getMessage());

        verify(subcategoryService, times(1)).uploadSubcategory(dto);
    }

    @Test
    @DisplayName("Should return a GetSubcategoryResponseDTO")
    void testGetSubcategorySucess() {
        GetSubcategoryResponseDTO dto = new GetSubcategoryResponseDTO(
            "Test", 
            "TestDescription", 
            EventCategory.TECHNOLOGY, 
            LocalDateTime.now(), 
            LocalDateTime.now());

        when(subcategoryService.getSubcategory("Test")).thenReturn(dto);

        ResponseEntity<GetSubcategoryResponseDTO> response = subcategoryController.getSubcategory("Test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(dto.name(), response.getBody().name());
        assertEquals(dto.description(), response.getBody().description());
        assertEquals(dto.eventCategory(), response.getBody().eventCategory());
        assertEquals(dto.createdAt(), response.getBody().createdAt());
        assertEquals(dto.updateAt(), response.getBody().updateAt());

        verify(subcategoryService, times(1)).getSubcategory("Test");
    }

    @Test
    @DisplayName("Should throwns an exception when subcategory is not found")
    void testGetSubcategoryThrowsException() {
        when(subcategoryService.getSubcategory("NotFound")).thenThrow(new RuntimeException("Error while trying to find subcategory"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            subcategoryController.getSubcategory("NotFound");
        });

        assertEquals("Error while trying to find subcategory", exception.getMessage());

        verify(subcategoryService, times(1)).getSubcategory("NotFound");
    }

    @Test
    @DisplayName("Should return a list of GetSubcategoryResponseDTO")
    void testGetSubcategoriesSucess() {
        GetSubcategoryResponseDTO dto1 = new GetSubcategoryResponseDTO(
            "Test1", 
            "TestDescription", 
            EventCategory.TECHNOLOGY, 
            LocalDateTime.now(), 
            LocalDateTime.now());

        GetSubcategoryResponseDTO dto2 = new GetSubcategoryResponseDTO(
            "Test2", 
            "TestDescription", 
            EventCategory.TECHNOLOGY, 
            LocalDateTime.now(), 
            LocalDateTime.now());

        List<GetSubcategoryResponseDTO> subcategories = List.of(dto1, dto2);

        when(subcategoryService.getListOfSubcategories()).thenReturn(subcategories);

        ResponseEntity<List<GetSubcategoryResponseDTO>> response = subcategoryController.getSubcategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Test1", response.getBody().get(0).name());
        assertEquals("Test2", response.getBody().get(1).name());

        verify(subcategoryService, times(1)).getListOfSubcategories();
    }

    @Test
    @DisplayName("Should return error 500 when not found subcategories")
    void testGetSubcategoriesThrowsException() {
        when(subcategoryService.getListOfSubcategories()).thenThrow(new RuntimeException("Error while trying to find subcategories"));
        
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            subcategoryController.getSubcategories()
        );

        assertEquals("Error while trying to find subcategories", exception.getMessage());

        verify(subcategoryService, times(1)).getListOfSubcategories();
    }

    @Test
    @DisplayName("Should return a UpdateSubcategoryRequestDTO")
    void testUpdateSubcategorySucess() {
        UpdateSubcategoryRequestDTO dto = new UpdateSubcategoryRequestDTO(
            "Test", 
            "TestUpdate", 
            "testDescription", 
            EventCategory.EDUCATION);

        doNothing().when(subcategoryService).updateSubcategory(dto);

        ResponseEntity<UpdateSubcategoryRequestDTO> response = subcategoryController.updateSubcategory(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(dto, response.getBody());

        verify(subcategoryService, times(1)).updateSubcategory(dto);
    }

    @Test
    @DisplayName("Should throw ConstraintViolationException when DTO is invalid")
    void testUpdateSubcategoryWithInvalidDTO() {
        UpdateSubcategoryRequestDTO dto = new UpdateSubcategoryRequestDTO(
            "NotFound", 
            "Test", 
            "testDescription", 
            EventCategory.EDUCATION);

        doThrow(new RuntimeException("Error while trying to find subcategory"))
            .when(subcategoryService).updateSubcategory(dto);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            subcategoryController.updateSubcategory(dto);
        });

        assertEquals("Error while trying to find subcategory", exception.getMessage());

        verify(subcategoryService, times(1)).updateSubcategory(any());
    }

    @Test
    @DisplayName("Should return 200 OK when deleting an existing subcategory successfully")
    void testDeleteSubcategorySuccess() {
        String existingSubcategory = "ValidSubategory";

        doNothing().when(subcategoryService).deleteSubcategory(existingSubcategory);

        ResponseEntity<String> response = subcategoryController.deleteSubcategory(existingSubcategory);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Subcategory deleted succesfuly.", response.getBody());

        verify(subcategoryService, times(1)).deleteSubcategory(existingSubcategory);
    }

    @Test
    @DisplayName("Should throw RuntimeException when trying to delete a non-existent subcategory")
    void testDeleteSubcategoryNotFound() {
        String nonExistentSubcategory = "NonExistentSubcategory";

        doThrow(new RuntimeException("Error while trying to find subcategory"))
            .when(subcategoryService).deleteSubcategory(nonExistentSubcategory);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            subcategoryController.deleteSubcategory(nonExistentSubcategory);
        });

        assertEquals("Error while trying to find subcategory", exception.getMessage());

        verify(subcategoryService, times(1)).deleteSubcategory(nonExistentSubcategory);
    }

}
