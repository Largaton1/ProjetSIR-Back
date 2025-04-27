package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.domain.Administrateur;
import fr.istic.taa.jaxrs.domain.Organisateur;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class OrganisateurDao extends AbstractJpaDao<Long, Organisateur> {
    public OrganisateurDao() {
        super(Organisateur.class);
    }

    public Organisateur findByEmail(String email) {
        try {
            TypedQuery<Organisateur> query = this.entityManager.createQuery(
                "SELECT a FROM Organisateur a WHERE a.email = :email", Organisateur.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
