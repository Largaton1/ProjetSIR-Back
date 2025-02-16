package fr.istic.taa.jaxrs.service;

import java.util.List;
import java.util.stream.Collectors;

import fr.istic.taa.jaxrs.dao.*;
import fr.istic.taa.jaxrs.domain.*;
import fr.istic.taa.jaxrs.dto.*;

public class EvenementService {
    

    private OrganisateurDao organisateurDao;

    private EvenementDao evenementDao;

    private TicketDao ticketDao;

    public EvenementService() {
        organisateurDao = new OrganisateurDao();
        evenementDao = new EvenementDao();
        ticketDao = new TicketDao();
    }


    // Ajouter un événement
    public EvenementDto createEvenement(EvenementDto evenementDto) {
        Evenement evenement = new Evenement();
        evenement.setNomEvent(evenementDto.getNomEvent());
        evenement.setDate(evenementDto.getDate());
        evenement.setLieu(evenementDto.getLieu());
        evenement.setDescription(evenementDto.getDescription());
        evenement.setCapacite(evenementDto.getCapacite());
        evenement.setStatut(evenementDto.getStatut());
        evenement.setTickets(ticketDao.findAll());
        //evenement.setOrganisateur(organisateurDao.findOne(evenementDto.getOrganisateur()));
        //evenement.setOrganisateur(organisateurDao.findOne(1L)); // Organisateur par défaut
        evenementDao.save(evenement);
        return new EvenementDto(evenement.getId(), evenement.getNomEvent(), evenement.getDate(), evenement.getLieu(), evenement.getDescription(), evenement.getCapacite(), evenement.getStatut(), null, evenement.getOrganisateur());
    }

    // Récupérer un événement par ID
    public EvenementDto getEvenementById(long id) {
        Evenement evenement = evenementDao.findOne(id);
        if (evenement != null) {
            return new EvenementDto(evenement.getId(), evenement.getNomEvent(), evenement.getDate(), evenement.getLieu(), evenement.getDescription(), evenement.getCapacite(), evenement.getStatut(), null, evenement.getOrganisateur());
        }
        return null;
    }

    // Récupérer tous les événements
    public List<EvenementDto> getAllEvenements() {
        return evenementDao.findAll().stream()
                .map(evenement -> new EvenementDto(evenement.getId(), evenement.getNomEvent(), evenement.getDate(), evenement.getLieu(), evenement.getDescription(), evenement.getCapacite(), evenement.getStatut(), null, evenement.getOrganisateur()))
                .collect(Collectors.toList());
    }

    // Mettre à jour un événement
    public EvenementDto updateEvenement(long id, EvenementDto dto) {
        Evenement evenement = evenementDao.findOne(id);
        if (evenement != null) {
            evenement.setNomEvent(dto.getNomEvent());
            evenement.setDate(dto.getDate());
            evenement.setLieu(dto.getLieu());
            evenement.setDescription(dto.getDescription());
            evenement.setCapacite(dto.getCapacite());
            evenement.setStatut(dto.getStatut());
            //evenement.setOrganisateur(organisateurDao.findOne(dto.getOrganisateur())); // Organisateur ne doit pas changer
            evenementDao.update(evenement);
            return new EvenementDto(evenement.getId(), evenement.getNomEvent(), evenement.getDate(), evenement.getLieu(), evenement.getDescription(), evenement.getCapacite(), evenement.getStatut(), null, null);
        }
        return null;
    }

    // Supprimer un événement
    public void deleteEvenement(long id) {
        Evenement evenement = evenementDao.findOne(id);
        if (evenement != null) {
            evenementDao.delete(evenement);
        }
    }


}
