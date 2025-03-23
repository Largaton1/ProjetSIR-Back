package fr.istic.taa.jaxrs.service;

import java.util.List;
import java.util.stream.Collectors;

import fr.istic.taa.jaxrs.dao.AdministrateurDao;
import fr.istic.taa.jaxrs.domain.Administrateur;
import fr.istic.taa.jaxrs.dto.AdministrateurDto;

public class AdministrateurService {

    private AdministrateurDao administrateurDao;

    

    public AdministrateurService() {
        this.administrateurDao = new AdministrateurDao();
    }
    
    // Ajouter un administrateur
    public AdministrateurDto createAdministrateur(AdministrateurDto dtoadmin) {
        Administrateur admin = new Administrateur();
        admin.setNom(dtoadmin.getNom());
        admin.setPrenom(dtoadmin.getPrenom());
        admin.setEmail(dtoadmin.getEmail());
        admin.setPassword(dtoadmin.getPassword());
        administrateurDao.save(admin);
        return new AdministrateurDto(admin.getId(), admin.getNom(), admin.getPrenom(), admin.getEmail(), admin.getPassword(), null);
    }

    // Récupérer un administrateur par ID
    public AdministrateurDto getAdministrateurById(long id) {
        Administrateur admin = administrateurDao.findOne(id);
        if (admin != null) {
            return new AdministrateurDto(admin.getId(), admin.getNom(), admin.getPrenom(), admin.getEmail(), admin.getPassword(), null);
        }
        throw new RuntimeException("Administrateur non trouvé");
    }

    // Récupérer tous les administrateurs
    public List<AdministrateurDto> getAllAdministrateurs() {
        return administrateurDao.findAll().stream()
                .map(admin -> new AdministrateurDto(admin.getId(), admin.getNom(), admin.getPrenom(), admin.getEmail(), admin.getPassword(), null))
                .collect(Collectors.toList());
    }

     // Mettre à jour un administrateur
    public AdministrateurDto updateAdministrateur(long id, AdministrateurDto dto) {
        Administrateur admin = administrateurDao.findOne(id);
        if (admin != null) {
            admin.setNom(dto.getNom());
            admin.setPrenom(dto.getPrenom());
            admin.setEmail(dto.getEmail());
            admin.setPassword(dto.getPassword()); 
            administrateurDao.update(admin);
            return new AdministrateurDto(admin.getId(), admin.getNom(), admin.getPrenom(), admin.getEmail(), admin.getPassword(), null);
        }
        throw new RuntimeException("Administrateur non trouvé");
    }

     // Supprimer un administrateur
     public void deleteAdministrateur(long id) {
        Administrateur admin = administrateurDao.findOne(id);
        if (admin != null) {
            administrateurDao.delete(admin);
            
        } else {
            throw new RuntimeException("Administrateur non trouvé");
        }
       
    }
}
