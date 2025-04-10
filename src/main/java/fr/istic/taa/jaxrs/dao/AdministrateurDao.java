package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.domain.Administrateur;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class AdministrateurDao extends AbstractJpaDao<Long, Administrateur> {
    public AdministrateurDao() {
        super(Administrateur.class);
    }


    public Administrateur findByEmail(String email) {
        try {
            TypedQuery<Administrateur> query = this.entityManager.createQuery(
                "SELECT a FROM Administrateur a WHERE a.email = :email", Administrateur.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
 
    
}
