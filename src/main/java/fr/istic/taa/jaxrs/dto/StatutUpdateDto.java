package fr.istic.taa.jaxrs.dto;


public class StatutUpdateDto {
    private String nouveauStatut;
    private Long administrateurId;

    public String getNouveauStatut() {
        return nouveauStatut;
    }

    public void setNouveauStatut(String nouveauStatut) {
        this.nouveauStatut = nouveauStatut;
    }

    public Long getAdministrateurId() {
        return administrateurId;
    }

    public void setAdministrateurId(Long administrateurId) {
        this.administrateurId = administrateurId;
    }
}

