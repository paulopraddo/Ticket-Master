package com.ticketmaster.api.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketmaster.api.domain.category.model.Subcategory;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, String> {

    Subcategory findByName(String name);
}
