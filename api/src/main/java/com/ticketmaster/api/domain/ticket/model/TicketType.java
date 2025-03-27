package com.ticketmaster.api.domain.ticket.model;

public enum TicketType {

    NORMAL("NORMAL"),
    VIP("VIP"),
    CAMAROTE("CAMAROTE");

    private String type;

    TicketType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
}
