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

@Path("/admin")
@Produces({"application/json"})
public class AministrateurResource {

  AdministrateurService administrateurService = new AdministrateurService();



@GET
@Path("/{id}")
@Produces(MediaType.APPLICATION_JSON) // On indique que la réponse est en HTML
public Response getAdministrateurById(@PathParam("id") Long id) {
    AdministrateurDto admin = administrateurService.getAdministrateurById(id);

    if (admin == null) {
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"message\": \"Aucun administrateur trouvé.\"}")
                       .build();
    }

    return Response.ok(admin).build();
}


@GET
@Path("/")
@Produces(MediaType.APPLICATION_JSON) // Indique qu'on retourne du JSON
public Response getAllAdministrateurs() {
    List<AdministrateurDto> administrateurs = administrateurService.getAllAdministrateurs();

    if (administrateurs.isEmpty()) {
        return Response.ok("{\"message\": \"Aucun administrateur trouvé.\"}").build();
    }

    return Response.ok(administrateurs).build();
}


@POST
@Path("/add")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED) // Accepte les données de formulaire
@Produces(MediaType.APPLICATION_JSON) // Retourne du JSON
public Response createAdmin(
      AdministrateurDto admin) {

    // Sauvegarde via le service
    administrateurService.createAdministrateur(admin);

    // Retourner une réponse JSON
    return Response.status(Response.Status.CREATED)
                   .entity(admin)
                   .build();
}

@PUT
@Path("/update/{id}")
@Consumes(MediaType.APPLICATION_JSON) // Accepte les données en JSON
@Produces(MediaType.APPLICATION_JSON) // Retourne du JSON
public Response updateAdmin(@PathParam("id") long id, AdministrateurDto adminDetails) {
    if (adminDetails == null) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"Données invalides\"}")
                .build();
    }

    // Récupération de l'administrateur existant
    AdministrateurDto admindto = administrateurService.getAdministrateurById(id);
    if (admindto == null) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Administrateur non trouvé\"}")
                .build();
    }

    // Mise à jour des champs (vérifier null)
    if (adminDetails.getNom() != null && !adminDetails.getNom().isEmpty()) {
        admindto.setNom(adminDetails.getNom());
    }
    if (adminDetails.getPrenom() != null && !adminDetails.getPrenom().isEmpty()) {
        admindto.setPrenom(adminDetails.getPrenom());
    }
    if (adminDetails.getEmail() != null && !adminDetails.getEmail().isEmpty()) {
        admindto.setEmail(adminDetails.getEmail());
    }
    if (adminDetails.getPassword() != null && !adminDetails.getPassword().isEmpty()) {
        admindto.setPassword(adminDetails.getPassword());
    }

    // Mise à jour dans la base de données
    administrateurService.updateAdministrateur(id, admindto);

    // Retourner l'administrateur mis à jour
    return Response.ok(admindto).build();
}



@DELETE
@Path("/delete/{id}")
public Response deleteAdmin(@PathParam("id") Long id, @Context UriInfo uriInfo) {
    try {
        administrateurService.deleteAdministrateur(id);
        return Response.ok().entity("Administrateur supprimé avec succès.").build();
    } catch (IllegalArgumentException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }

}




}