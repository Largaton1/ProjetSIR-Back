package fr.istic.taa.jaxrs.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import fr.istic.taa.jaxrs.domain.Evenement;

public class OrganisateurDto extends PersonneDto{
    
    //private long id;
    private List<EvenementDto> evenements;

    public OrganisateurDto() {
        super();
    }

    public OrganisateurDto(long id, String nom, String prenom, String email, String password, List<EvenementDto> evenements) {
        super(id, nom, prenom, email, password);
        this.evenements = evenements;
    }

    @JsonManagedReference
    public List<EvenementDto> getEvenements() {
        return evenements;
    }

    @JsonManagedReference
    public void setEvenements(List<EvenementDto> evenements) {
        this.evenements = evenements;
    }
}
