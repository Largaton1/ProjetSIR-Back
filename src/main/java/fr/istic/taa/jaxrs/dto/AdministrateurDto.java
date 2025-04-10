package fr.istic.taa.jaxrs.dto;

import java.util.List;

import fr.istic.taa.jaxrs.domain.Administrateur;
import fr.istic.taa.jaxrs.domain.Organisateur;

public class AdministrateurDto extends PersonneDto {
    //private long id;
    private List<EvenementDto> evenement; 

    public AdministrateurDto() {
        super();
    }

   
    public AdministrateurDto(long id,String nom, String prenom, String email, String password, List<EvenementDto> evenement) {
        super(id, nom, prenom, email, password);
        this.evenement = evenement;
    }

     public static AdministrateurDto fromEntityAdmin(Administrateur administrateur) {
    if (administrateur == null) {
        return null;
    }

    return new AdministrateurDto(
        administrateur.getId(),
        administrateur.getNom(),
        administrateur.getPrenom(),
        administrateur.getEmail(),
        null,
        null // On évite la récursion infinie en ne convertissant pas les événements ici
    );
}

    

    public List<EvenementDto> getEvenement() { 
        return evenement;
    }
    

    public void setEvenement(List<EvenementDto> evenement) {
         this.evenement = evenement;
     }
}

