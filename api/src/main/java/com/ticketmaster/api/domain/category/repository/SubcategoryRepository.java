package com.ticketmaster.api.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketmaster.api.domain.category.model.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, String> {

}
