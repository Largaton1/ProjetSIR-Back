package fr.istic.taa.jaxrs.service;

import java.util.List;
import java.util.stream.Collectors;

import fr.istic.taa.jaxrs.dao.*;
import fr.istic.taa.jaxrs.domain.*;
import fr.istic.taa.jaxrs.dto.*;

public class OrganisateurService {
    
    private OrganisateurDao organisateurDao;

    public OrganisateurService() {
        organisateurDao = new OrganisateurDao();
    }


    public OrganisateurDto loginOrganisateur(String email, String password) {
        Organisateur organisateur = organisateurDao.findByEmail(email);

        if (organisateur != null && organisateur.getPassword().equals(password)) {
            return new OrganisateurDto(
                organisateur.getId(),
                organisateur.getNom(),
                organisateur.getPrenom(),
                organisateur.getEmail(),
                null, // On ne retourne pas le mot de passe
                null
            );
        } else {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }
    }

    // Ajouter un organisateur
    public OrganisateurDto createOrganisateur(OrganisateurDto organisateurDto) {
        Organisateur organisateur = new Organisateur();
        organisateur.setNom(organisateurDto.getNom());
        organisateur.setPrenom(organisateurDto.getPrenom());
        organisateur.setEmail(organisateurDto.getEmail());
        organisateur.setPassword(organisateurDto.getPassword());
        organisateurDao.save(organisateur);
        return new OrganisateurDto(organisateur.getId(), organisateur.getNom(), organisateur.getPrenom(), organisateur.getEmail(), organisateur.getPassword(), null);
    }

    // Récupérer un organisateur par ID
    public OrganisateurDto getOrganisateurById(long id) {
        Organisateur organisateur = organisateurDao.findOne(id);
        if (organisateur != null) {
            return new OrganisateurDto(organisateur.getId(), organisateur.getNom(), organisateur.getPrenom(), organisateur.getEmail(), organisateur.getPassword(), null);
        }
        return null;
    }

    // Récupérer tous les organisateurs
    public List<OrganisateurDto> getAllOrganisateurs() {
        return organisateurDao.findAll().stream()
                .map(organisateur -> new OrganisateurDto(organisateur.getId(), organisateur.getNom(), organisateur.getPrenom(), organisateur.getEmail(), organisateur.getPassword(), null))
                .collect(Collectors.toList());
    }

    // Mettre à jour un organisateur
    public OrganisateurDto updateOrganisateur(long id, OrganisateurDto dto) {
        Organisateur organisateur = organisateurDao.findOne(id);
        if (organisateur != null) {
            organisateur.setNom(dto.getNom());
            organisateur.setPrenom(dto.getPrenom());
            organisateur.setEmail(dto.getEmail());// Email ne doit pas changer
            organisateur.setPassword(dto.getPassword()); 
            organisateurDao.update(organisateur);
            return new OrganisateurDto(organisateur.getId(), organisateur.getNom(), organisateur.getPrenom(), organisateur.getEmail(), organisateur.getPassword(), null);
        }
        return null;
    }

    // Supprimer un organisateur
    public void deleteOrganisateur(long id) {
        Organisateur organisateur = organisateurDao.findOne(id);
        if (organisateur != null) {
            organisateurDao.delete(organisateur);
        }
    }

}
