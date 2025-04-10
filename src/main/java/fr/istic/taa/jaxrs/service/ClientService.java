package fr.istic.taa.jaxrs.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import fr.istic.taa.jaxrs.dao.ClientDao;
import fr.istic.taa.jaxrs.dao.TicketDao;
import fr.istic.taa.jaxrs.domain.*;
import fr.istic.taa.jaxrs.dto.*;


public class ClientService {
    
    private ClientDao clientDao;
    private TicketDao ticketDao;

    public ClientService() {
        clientDao = new ClientDao();
        ticketDao = new TicketDao();
    }


    public ClientDto loginClient(String email, String password) {
        Client client = clientDao.findByEmail(email);

        if (client != null && client.getPassword().equals(password)) {
            return new ClientDto(
                client.getId(),
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                null, // On ne retourne pas le mot de passe
                null
            );
        } else {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }
    }

    public ClientDto createClient(ClientDto clientDto) {
        Client client = new Client();
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setEmail(clientDto.getEmail());
        client.setPassword(clientDto.getPassword());
        clientDao.save(client);
        return new ClientDto(client.getId(), client.getNom(), client.getPrenom(), client.getEmail(), client.getPassword(), null);
    }


     public ClientDto getClientById(Long id) {
        Client client = clientDao.findOne(id);
        if (client!=null) {
            
            List<TicketDto> tickets = client.getTicket().stream()
                .map(ticket -> new TicketDto(ticket.getId(), ticket.getPrix(), ticket.getNumPlace(), ticket.isEstValide(), 
                new EvenementDto(ticket.getEvenement().getId(), 
                    ticket.getEvenement().getNomEvent(), 
                    ticket.getEvenement().getDate(), 
                    ticket.getEvenement().getLieu(), 
                    ticket.getEvenement().getDescription(), 
                    ticket.getEvenement().getCapacite(), 
                    ticket.getEvenement().getStatut(), 
                     // Pas besoin de la liste des tickets ici
                    null,   // Organisateur non inclus pour éviter les références circulaires
                    null
                ),  
                    new ClientDto(ticket.getClient().getId(), ticket.getClient().getNom(), ticket.getClient().getPrenom(),
                                ticket.getClient().getEmail(), ticket.getClient().getPassword(), null)
                    ))
                .collect(Collectors.toList());

            return new ClientDto(client.getId(), client.getNom(), client.getPrenom(), client.getEmail(), client.getPassword(), tickets);
        }
        return null;
    }

    public List<ClientDto> getAllClients() {
        List<Client> clients = clientDao.findAll();
        return clients.stream().map(client -> 
            new ClientDto(client.getId(), client.getNom(), client.getPrenom(), client.getEmail(), client.getPassword(), null)) 
            .collect(Collectors.toList());
    }
    

    public ClientDto updateClient(Long id, ClientDto clientDto) {
        Client client = clientDao.findOne(id);
        if (client != null) {
            client.setNom(clientDto.getNom());
            client.setPrenom(clientDto.getPrenom());
            client.setEmail(clientDto.getEmail());
            client.setPassword(clientDto.getPassword());
            clientDao.update(client);
            return new ClientDto(client.getId(), client.getNom(), client.getPrenom(),
                                 client.getEmail(), client.getPassword(),null);
        }
        return null;
    }

    // Supprimer un client
    public void deleteClient(long id) {
        Client client = clientDao.findOne(id);
        if (client != null) {
            clientDao.delete(client);
        }
    }
}
