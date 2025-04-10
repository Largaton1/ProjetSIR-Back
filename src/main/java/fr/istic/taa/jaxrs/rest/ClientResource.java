package fr.istic.taa.jaxrs.rest;

import java.net.URI;
import java.util.List;
import fr.istic.taa.jaxrs.dao.AdministrateurDao;
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

@Path("/client")
@Produces({"application/json"})
public class ClientResource {

  ClientService clientService = new ClientService();



@GET
@Path("/{id}")
@Produces(MediaType.APPLICATION_JSON) // On indique que la réponse est en HTML
public Response getClientById(@PathParam("id") Long id) {
    ClientDto client = clientService.getClientById(id);

    if (client == null) {
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"message\": \"Aucun client trouvé.\"}")
                       .build();
    }

    return Response.ok(client).build();
}


@GET
@Path("/")
@Produces(MediaType.APPLICATION_JSON) // Indique qu'on retourne du JSON
public Response getAllClients() {
    List<ClientDto> clients = clientService.getAllClients();

    if (clients.isEmpty()) {
        return Response.ok("{\"message\": \"Aucun client trouvé.\"}").build();
    }

    return Response.ok(clients).build();
}

@POST
@Path("/login")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public Response loginClient(@FormParam("email") String email,
@FormParam("password") String password) {
    try {
        ClientDto client = clientService.loginClient(email, password);
        return Response.ok(client).build();
    } catch (RuntimeException e) {
        return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Login échoué : " + e.getMessage())
                        .build();
    }
}

@POST
@Path("/add")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED) // Accepte les données de formulaire
@Produces(MediaType.APPLICATION_JSON) // Retourne du JSON
public Response createClient(
    @FormParam("nom") String nom,
    @FormParam("prenom") String prenom,
    @FormParam("email") String email,
    @FormParam("password") String password) {

  
         // Création de l'AdministrateurDto
    ClientDto client = new ClientDto();
    client.setNom(nom);
    client.setPrenom(prenom);
    client.setEmail(email);
    client.setPassword(password);
    client.setTicket(List.of());
    // Sauvegarde via le service
    clientService.createClient(client);

    // Retourner une réponse JSON
    return Response.status(Response.Status.CREATED)
                   .entity(client)
                   .build();
}

@PUT
@Path("/update/{id}")
@Consumes(MediaType.APPLICATION_JSON) // Accepte les données en JSON
@Produces(MediaType.APPLICATION_JSON) // Retourne du JSON
public Response updateClient(@PathParam("id") long id, ClientDto clientDetails) {
    if (clientDetails == null) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"Données invalides\"}")
                .build();
    }

    // Récupération du client existant
    ClientDto clientdto = clientService.getClientById(id);
    if (clientdto == null) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Client non trouvé\"}")
                .build();
    }

    // Mise à jour des champs (vérifier null)
    if (clientDetails.getNom() != null && !clientDetails.getNom().isEmpty()) {
        clientdto.setNom(clientDetails.getNom());
    }
    if (clientDetails.getPrenom() != null && !clientDetails.getPrenom().isEmpty()) {
        clientdto.setPrenom(clientDetails.getPrenom());
    }
    if (clientDetails.getEmail() != null && !clientDetails.getEmail().isEmpty()) {
        clientdto.setEmail(clientDetails.getEmail());
    }
    if (clientDetails.getPassword() != null && !clientDetails.getPassword().isEmpty()) {
        clientdto.setPassword(clientDetails.getPassword());
    }
    // Mise à jour dans la base de données
    clientService.updateClient(id, clientdto);

    // Retourner le client mis à jour
    return Response.ok(clientdto).build();
}



@DELETE
@Path("/delete/{id}")
public Response deleteClient(@PathParam("id") Long id, @Context UriInfo uriInfo) {
    try {
        clientService.deleteClient(id);
        return Response.ok().entity("Client supprimé avec succès.").build();
    } catch (IllegalArgumentException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }

}




}