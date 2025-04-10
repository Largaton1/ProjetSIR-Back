package fr.istic.taa.jaxrs.dao;

import java.util.List;

import fr.istic.taa.jaxrs.domain.Administrateur;
import fr.istic.taa.jaxrs.domain.Client;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class ClientDao extends AbstractJpaDao<Long, Client> {
    public ClientDao() {
        super(Client.class);
    }

    
    //tout les clients qui sont proprietaire d'un ticket 
    public List<Client> getUserWithOwnerTicket() {
            
        String query = "SELECT distinct k.owner FROM Client as k";
        return this.entityManager.createQuery(query).getResultList();
        
    }

    public Client findByEmail(String email) {
        try {
            TypedQuery<Client> query = this.entityManager.createQuery(
                "SELECT a FROM Client a WHERE a.email = :email", Client.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
