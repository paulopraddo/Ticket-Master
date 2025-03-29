package com.ticketmaster.api.app.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketmaster.api.app.category.dtos.GetSubcategoryResponseDTO;
import com.ticketmaster.api.app.category.dtos.UpdateSubcategoryRequestDTO;
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

        this.subcategorySevice.uploadSubcategory(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<GetSubcategoryResponseDTO> getSubcategory(@PathVariable String name) {
        return ResponseEntity.ok().body(this.subcategorySevice.getSubcategory(name));
    }

    @GetMapping
    public ResponseEntity<List<GetSubcategoryResponseDTO>> getSubcategories() {

        List<GetSubcategoryResponseDTO> subcategories = this.subcategorySevice.getListOfSubcategories();

        return ResponseEntity.ok().body(subcategories);
    }


    @PutMapping
    public ResponseEntity<UpdateSubcategoryRequestDTO> updateSubcategory(@RequestBody UpdateSubcategoryRequestDTO dto) {
        
        this.subcategorySevice.updateSubcategory(dto);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteSubcategory(@PathVariable String name) {

        this.subcategorySevice.deleteSubcategory(name);

        return ResponseEntity.ok().body("Subcategory deleted succesfuly.");
    }

}
