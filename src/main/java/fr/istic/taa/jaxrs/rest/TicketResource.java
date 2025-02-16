package fr.istic.taa.jaxrs.rest;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import fr.istic.taa.jaxrs.dao.*;
import fr.istic.taa.jaxrs.domain.*;
import fr.istic.taa.jaxrs.dto.*;
import fr.istic.taa.jaxrs.service.*;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServlet;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/ticket")
@Produces({"application/json"})
public class TicketResource {

  TicketService ticketService = new TicketService();



@GET
@Path("/{id}")
@Produces(MediaType.APPLICATION_JSON) // On indique que la réponse est en HTML
public Response getTicketById(@PathParam("id") Long id) {
    TicketDto ticket = ticketService.getTicketById(id);

    if (ticket == null) {
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"message\": \"Aucun ticket trouvé.\"}")
                       .build();
    }

    return Response.ok(ticket).build();
}


@GET
@Path("/")
@Produces(MediaType.APPLICATION_JSON) // Indique qu'on retourne du JSON
public Response getAllTickets() {
    List<TicketDto> tickets = ticketService.getAllTickets();

    if (tickets.isEmpty()) {
        return Response.ok("{\"message\": \"Aucun ticket trouvé.\"}").build();
    }

    return Response.ok(tickets).build();
}


@POST
@Path("/add")
@Consumes(MediaType.APPLICATION_JSON) // Accepte les données de formulaire
@Produces(MediaType.APPLICATION_JSON) // Retourne du JSON
public Response createTicket(
    TicketDto ticket) {

    

    // Sauvegarde via le service
    ticketService.createTicket(ticket);

    // Retourner une réponse JSON
    return Response.status(Response.Status.CREATED)
                   .entity(ticket)
                   .build();
}

@PUT
@Path("/update/{id}")
@Consumes(MediaType.APPLICATION_JSON) // Accepte les données en JSON
@Produces(MediaType.APPLICATION_JSON) // Retourne du JSON
public Response updateTicket(@PathParam("id") long id, TicketDto ticketDetails) {
    if (ticketDetails == null) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"Données invalides\"}")
                .build();
    }

    // Récupération de l'e ticket existant
    TicketDto ticketdto = ticketService.getTicketById(id);
    if (ticketdto == null) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Ticket non trouvé\"}")
                .build();
    }
    // Mise à jour dans la base de données
    ticketService.updateTicket(id, ticketdto);

    // Retourner le ticket mis à jour
    return Response.ok(ticketdto).build();
}



@DELETE
@Path("/delete/{id}")
public Response deleteTicket(@PathParam("id") Long id, @Context UriInfo uriInfo) {
    try {
        ticketService.deleteTicket(id);
        return Response.ok().entity("Ticket supprimé avec succès.").build();
    } catch (IllegalArgumentException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }

}




}