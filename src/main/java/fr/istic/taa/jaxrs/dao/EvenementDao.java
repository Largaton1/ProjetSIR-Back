package fr.istic.taa.jaxrs.dao;

import java.util.List;

import fr.istic.taa.jaxrs.domain.Evenement;
import fr.istic.taa.jaxrs.domain.Organisateur;

public class EvenementDao extends AbstractJpaDao<Long, Evenement> {
    public EvenementDao() {
        super(Evenement.class);
    }

    public List<Evenement> findByOrganisateur(Organisateur organisateur) {
    return entityManager.createQuery(
        "SELECT e FROM Evenement e WHERE e.organisateur = :organisateur", Evenement.class)
        .setParameter("organisateur", organisateur)
        .getResultList();
    }

    
}
