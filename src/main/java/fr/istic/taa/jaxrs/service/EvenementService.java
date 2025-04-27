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

    private AdministrateurDao administrateurDao;

    public EvenementService() {
        organisateurDao = new OrganisateurDao();
        evenementDao = new EvenementDao();
        ticketDao = new TicketDao();
        administrateurDao = new AdministrateurDao();
    }


    // Ajouter un événement
    public EvenementDto createEvenement(EvenementDto evenementDto) {
        Evenement evenement = new Evenement();
        evenement.setNomEvent(evenementDto.getNomEvent());
        evenement.setDate(evenementDto.getDate());
        evenement.setLieu(evenementDto.getLieu());
        evenement.setDescription(evenementDto.getDescription());
        evenement.setCapacite(evenementDto.getCapacite());
        //evenement.setStatut(evenementDto.getStatut());
        //evenement.setTickets(ticketDao.findAll());
        evenement.setOrganisateur(organisateurDao.findOne(evenementDto.getOrganisateur().getId()));
        //evenement.setAdministrateur(administrateurDao.findOne(evenementDto.getAdministrateur().getId())); // Pas d'administrateur pour l'instant
        //evenement.setOrganisateur(organisateurDao.findOne(1L)); // Organisateur par défaut
        //evenement.setOrganisateur(evenementDto.getOrganisateur());


        // Vérifier si un administrateur est fourni
        if (evenementDto.getAdministrateur() != null) {
            // Si un administrateur est fourni, on le récupère
            evenement.setAdministrateur(administrateurDao.findOne(evenementDto.getAdministrateur().getId()));
        } else {
            // Si aucun administrateur n'est fourni, on laisse le champ null ou on n'associe aucun administrateur
            evenement.setAdministrateur(null); // Optional : laisser le champ administrateur vide
        }
        
        
        evenementDao.save(evenement);
        return new EvenementDto(evenement.getId(), evenement.getNomEvent(), evenement.getDate(), evenement.getLieu(), evenement.getDescription(), evenement.getCapacite(), OrganisateurDto.fromEntity(evenement.getOrganisateur()), evenement.getAdministrateur() != null ? AdministrateurDto.fromEntityAdmin(evenement.getAdministrateur()) : null);   
    }

    // Récupérer un événement par ID
    public EvenementDto getEvenementById(long id) {
        Evenement evenement = evenementDao.findOne(id);
        if (evenement != null) {
            return new EvenementDto(evenement.getId(), evenement.getNomEvent(), evenement.getDate(), evenement.getLieu(), evenement.getDescription(), evenement.getCapacite(), evenement.getStatut(), OrganisateurDto.fromEntity(evenement.getOrganisateur()), AdministrateurDto.fromEntityAdmin(evenement.getAdministrateur()));
        }
        return null;
    }


    public EvenementDto updateStatutEvenement(Long evenementId, String nouveauStatut, Long adminId) {
        Evenement evenement = evenementDao.findOne(evenementId);
        if (evenement == null) {
            throw new IllegalArgumentException("Événement non trouvé");
        }
    
        Administrateur admin = administrateurDao.findOne(adminId);
        if (admin == null) {
            throw new IllegalArgumentException("Administrateur non trouvé");
        }
    
        try {
            evenement.setStatut(statutEvent.valueOf(nouveauStatut)); // Vérifie que le statut est valide
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Statut invalide : " + nouveauStatut);
        }
    
        evenement.setAdministrateur(admin); // On enregistre l’admin qui modifie
        evenementDao.save(evenement);
    
        return new EvenementDto(
            evenement.getId(), 
            evenement.getNomEvent(), 
            evenement.getDate(), 
            evenement.getLieu(), 
            evenement.getDescription(), 
            evenement.getCapacite(), 
            evenement.getStatut(), 
            OrganisateurDto.fromEntity(evenement.getOrganisateur()), 
            AdministrateurDto.fromEntityAdmin(evenement.getAdministrateur())
        );
    }
    

    // Récupérer tous les événements
    public List<EvenementDto> getAllEvenements() {
        return evenementDao.findAll().stream()
                .map(evenement -> new EvenementDto(evenement.getId(), evenement.getNomEvent(), evenement.getDate(), evenement.getLieu(), evenement.getDescription(), evenement.getCapacite(), evenement.getStatut(), OrganisateurDto.fromEntity(evenement.getOrganisateur()), AdministrateurDto.fromEntityAdmin(evenement.getAdministrateur())))
                .collect(Collectors.toList());
    }


    // Récupérer tous les événements d'un organisateur
    public List<EvenementDto> getEvenementsByOrganisateurId(Long organisateurId) {
        Organisateur organisateur = organisateurDao.findOne(organisateurId);
        
        if (organisateur == null) {
            throw new IllegalArgumentException("Organisateur non trouvé avec l'ID : " + organisateurId);
        }
    
        List<Evenement> evenements = evenementDao.findByOrganisateur(organisateur);
    
        return evenements.stream()
                         .map(e -> new EvenementDto(
                             e.getId(),
                             e.getNomEvent(),
                             e.getDate(),
                             e.getLieu(),
                             e.getDescription(),
                             e.getCapacite(),
                             e.getStatut(),
                             OrganisateurDto.fromEntity(e.getOrganisateur()),
                             e.getAdministrateur() != null ? AdministrateurDto.fromEntityAdmin(e.getAdministrateur()) : null
                         ))
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
            return new EvenementDto(evenement.getId(), evenement.getNomEvent(), evenement.getDate(), evenement.getLieu(), evenement.getDescription(), evenement.getCapacite(), evenement.getStatut(), null,null);
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
