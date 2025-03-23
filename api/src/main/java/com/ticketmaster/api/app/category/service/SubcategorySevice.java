package com.ticketmaster.api.app.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketmaster.api.app.category.dtos.UploadSubcategoryRequestDTO;
import com.ticketmaster.api.domain.category.model.Subcategory;
import com.ticketmaster.api.domain.category.repository.SubcategoryRepository;

@Service
public class SubcategorySevice {

    @Autowired
    SubcategoryRepository subcategoryRepository;

    public void uploadCategory(UploadSubcategoryRequestDTO dto) {
        Subcategory model = Subcategory.builder()
        .name(dto.name())
        .description(dto.description())
        .eventCategory(dto.eventCategory())
        .build();

        this.subcategoryRepository.save(model);
    }
}
