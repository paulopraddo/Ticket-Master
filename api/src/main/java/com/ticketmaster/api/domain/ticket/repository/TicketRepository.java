package com.ticketmaster.api.domain.ticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketmaster.api.domain.ticket.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

    List<Ticket> findByEventId(String id);
}
