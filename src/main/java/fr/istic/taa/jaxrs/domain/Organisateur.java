package fr.istic.taa.jaxrs.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("Organisateur")
public class Organisateur extends Personne implements Serializable {
    private long id;
    private List<Evenement> evenements;

    public Organisateur() {
    }

    public Organisateur(long id, List<Evenement> evenements) {
        super();
        this.id = id;
        this.evenements = evenements;
    }

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "organisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    public List<Evenement> getEvenements() {
        return evenements;
    }

    @JsonManagedReference
    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }

   

    
}
