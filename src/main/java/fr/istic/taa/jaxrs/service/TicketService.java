package fr.istic.taa.jaxrs.service;

import java.util.List;
import java.util.stream.Collectors;
import fr.istic.taa.jaxrs.dao.*;
import fr.istic.taa.jaxrs.domain.*;
import fr.istic.taa.jaxrs.dto.*;

public class TicketService {
    
    private TicketDao ticketDao;

    private EvenementDao evenementDao;

    private ClientDao clientDao;


    public TicketService() {
        ticketDao = new TicketDao();
        evenementDao = new EvenementDao();
        clientDao = new ClientDao();
    }

    // Ajouter un ticket
    public TicketDto createTicket(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setPrix(ticketDto.getPrix());
        ticket.setNumPlace(ticketDto.getNumPlace());
        ticket.setEstValide(ticketDto.isEstValide());
        ticket.setEvenement(evenementDao.findOne(ticketDto.getEvenement().getId()));
        ticket.setClient(clientDao.findOne(ticketDto.getClient().getId()));
        ticketDao.save(ticket);
        return new TicketDto(ticket.getId(), ticket.getPrix(), ticket.getNumPlace(), ticket.isEstValide(), 
        new EvenementDto(ticket.getEvenement().getId(), ticket.getEvenement().getNomEvent(), ticket.getEvenement().getDate(), ticket.getEvenement().getDescription(), 
        ticket.getEvenement().getLieu(),ticket.getEvenement().getCapacite(), ticket.getEvenement().getStatut(), OrganisateurDto.fromEntity(ticket.getEvenement().getOrganisateur()), AdministrateurDto.fromEntityAdmin(ticket.getEvenement().getAdministrateur()) ),
         null);
    }

    // Récupérer un ticket par ID
    public TicketDto getTicketById(long id) {
        Ticket ticket = ticketDao.findOne(id);
        if (ticket != null) {
            return new TicketDto(ticket.getId(), ticket.getPrix(), ticket.getNumPlace(), ticket.isEstValide(), 
            new EvenementDto(ticket.getEvenement().getId(), ticket.getEvenement().getNomEvent(), ticket.getEvenement().getDate(), ticket.getEvenement().getDescription(), 
            ticket.getEvenement().getLieu(),ticket.getEvenement().getCapacite(), ticket.getEvenement().getStatut(), OrganisateurDto.fromEntity(ticket.getEvenement().getOrganisateur()), AdministrateurDto.fromEntityAdmin(ticket.getEvenement().getAdministrateur()) ),
             null);
        }
        return null;
    }

    // Récupérer tous les tickets
    public List<TicketDto> getAllTickets() {
        return ticketDao.findAll().stream()
                .map(ticket -> new TicketDto(ticket.getId(), ticket.getPrix(), ticket.getNumPlace(), ticket.isEstValide(), 
                new EvenementDto(ticket.getEvenement().getId(), ticket.getEvenement().getNomEvent(), ticket.getEvenement().getDate(), ticket.getEvenement().getDescription(), 
                ticket.getEvenement().getLieu(),ticket.getEvenement().getCapacite(), ticket.getEvenement().getStatut(), OrganisateurDto.fromEntity(ticket.getEvenement().getOrganisateur()), AdministrateurDto.fromEntityAdmin(ticket.getEvenement().getAdministrateur()) ),
                 null))
                .collect(Collectors.toList());
    }

    // Mettre à jour un ticket
    public TicketDto updateTicket(long id, TicketDto dto) {
        Ticket ticket = ticketDao.findOne(id);
        if (ticket != null) {
            ticket.setPrix(dto.getPrix());
            ticket.setNumPlace(dto.getNumPlace());
            ticket.setEstValide(dto.isEstValide());
            ticketDao.update(ticket);
            return new TicketDto(ticket.getId(), ticket.getPrix(), ticket.getNumPlace(), ticket.isEstValide(), 
            new EvenementDto(ticket.getEvenement().getId(), ticket.getEvenement().getNomEvent(), ticket.getEvenement().getDate(), ticket.getEvenement().getDescription(), 
            ticket.getEvenement().getLieu(),ticket.getEvenement().getCapacite(), ticket.getEvenement().getStatut(), OrganisateurDto.fromEntity(ticket.getEvenement().getOrganisateur()), AdministrateurDto.fromEntityAdmin(ticket.getEvenement().getAdministrateur()) ),
             null);
        }
        return null;
    }

    // Supprimer un ticket
    public void deleteTicket(long id) {
        Ticket ticket = ticketDao.findOne(id);
        if (ticket != null) {
            ticketDao.delete(ticket);
        }
    }
}
