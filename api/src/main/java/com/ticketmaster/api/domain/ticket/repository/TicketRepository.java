package com.ticketmaster.api.domain.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketmaster.api.domain.ticket.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

}
