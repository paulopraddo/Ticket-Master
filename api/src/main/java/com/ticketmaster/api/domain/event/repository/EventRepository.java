package com.ticketmaster.api.domain.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketmaster.api.domain.event.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

}
