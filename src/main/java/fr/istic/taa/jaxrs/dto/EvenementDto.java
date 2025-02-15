package fr.istic.taa.jaxrs.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.statutEvent;

public class EvenementDto {
    

    private Long id;
    private String nomEvent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private String lieu;
    private String description;
    private int capacite;
    private statutEvent statut;
    private List<TicketDto> tickets;
    private Organisateur organisateur;




    public EvenementDto() {
    }

    public EvenementDto(Long id, String nomEvent, LocalDateTime date, String lieu, String description, int capacite,
            statutEvent statut, List<TicketDto> tickets, Organisateur organisateur) {
        this.id = id;
        this.nomEvent = nomEvent;
        this.date = date;
        this.lieu = lieu;
        this.description = description;
        this.capacite = capacite;
        this.statut = statut;
        this.tickets = tickets;
        this.organisateur = organisateur;
    }



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNomEvent() {
        return nomEvent;
    }
    public void setNomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getLieu() {
        return lieu;
    }
    public void setLieu(String lieu) {
        this.lieu = lieu;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getCapacite() {
        return capacite;
    }
    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }
    public statutEvent getStatut() {
        return statut;
    }
    public void setStatut(statutEvent statut) {
        this.statut = statut;
    }
    public List<TicketDto> getTickets() {
        return tickets;
    }
    public void setTickets(List<TicketDto> tickets) {
        this.tickets = tickets;
    }
    public Organisateur getOrganisateur() {
        return organisateur;
    }
    public void setOrganisateur(Organisateur organisateur) {
        this.organisateur = organisateur;
    }
}
