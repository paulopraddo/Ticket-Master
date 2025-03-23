package com.ticketmaster.api.app.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketmaster.api.app.category.dtos.UploadSubcategoryRequestDTO;
import com.ticketmaster.api.app.category.service.SubcategorySevice;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class EventCategoryController {

    @Autowired
    private SubcategorySevice subcategorySevice;

    @PostMapping
    public ResponseEntity<String> uploadSubcategory(@RequestBody @Valid UploadSubcategoryRequestDTO dto) {

        this.subcategorySevice.uploadCategory(dto);

        return ResponseEntity.ok().build();
    }
}
