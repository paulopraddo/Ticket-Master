package com.ticketmaster.api.domain.ticket.model;

import com.ticketmaster.api.domain.event.model.Event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tickets")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quanty;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    public Ticket(TicketType ticketType, Double price, Integer quanty, Event event) {
        this.ticketType = ticketType;
        this.price = price;
        this.quanty = quanty;
        this.event = event;
    }

}
