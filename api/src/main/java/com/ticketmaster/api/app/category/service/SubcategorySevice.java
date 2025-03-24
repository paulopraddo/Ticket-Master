package com.ticketmaster.api.app.category.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketmaster.api.app.category.dtos.GetSubcategoryDTO;
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

    public GetSubcategoryDTO getSubcategory(String name) {
        Subcategory subcategory = this.subcategoryRepository.findByName(name);

        if(subcategory == null) {
            throw new RuntimeException("Error while trying to find subcategory");
        }

        return GetSubcategoryDTO
            .builder()
            .name(subcategory.getName())
            .description(subcategory.getDescription())
            .eventCategory(subcategory.getEventCategory())
            .createdAt(subcategory.getCreatedAt())
            .updateAt(subcategory.getUpdatedAt())
            .build();
    }

    public List<GetSubcategoryDTO> getListOfSubcategories() {
        List<Subcategory> subcategories = this.subcategoryRepository.findAll();

        List<GetSubcategoryDTO> subcategoryDtos = subcategories
        .stream()
        .map(subcategory -> 
            GetSubcategoryDTO
            .builder()
            .name(subcategory.getName())
            .description(subcategory.getDescription())
            .eventCategory(subcategory.getEventCategory())
            .createdAt(subcategory.getCreatedAt())
            .updateAt(subcategory.getUpdatedAt())
            .build())
        .collect(Collectors.toList());
        
        return subcategoryDtos;
    }
}
