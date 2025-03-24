package com.ticketmaster.api.app.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketmaster.api.app.category.dtos.GetSubcategoryDTO;
import com.ticketmaster.api.app.category.dtos.UploadSubcategoryRequestDTO;
import com.ticketmaster.api.app.category.service.SubcategorySevice;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class SubcategoryController {

    @Autowired
    private SubcategorySevice subcategorySevice;

    @PostMapping
    public ResponseEntity<String> uploadSubcategory(@RequestBody @Valid UploadSubcategoryRequestDTO dto) {

        this.subcategorySevice.uploadCategory(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<GetSubcategoryDTO> getSubcategory(@PathVariable String name) {
        return ResponseEntity.ok().body(this.subcategorySevice.getSubcategory(name));
    }

    @GetMapping
    public ResponseEntity<List<GetSubcategoryDTO>> getSubcategories() {

        List<GetSubcategoryDTO> subcategories = this.subcategorySevice.getListOfSubcategories();

        return ResponseEntity.ok().body(subcategories);
    }

}
