package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.domain.Ticket;

public class TicketDao extends AbstractJpaDao<Long, Ticket> {
    public TicketDao() {
        super(Ticket.class);
    }
    
}
