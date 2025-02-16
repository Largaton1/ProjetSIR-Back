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

@Path("/organisateur")
@Produces({"application/json"})
public class OrganisateurResource {

  OrganisateurService organisateurService = new OrganisateurService();



@GET
@Path("/{id}")
@Produces(MediaType.APPLICATION_JSON) // On indique que la réponse est en HTML
public Response getOrganisateurById(@PathParam("id") Long id) {
    OrganisateurDto organisateur = organisateurService.getOrganisateurById(id);

    if (organisateur == null) {
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"message\": \"Aucun organisateur trouvé.\"}")
                       .build();
    }

    return Response.ok(organisateur).build();
}


@GET
@Path("/")
@Produces(MediaType.APPLICATION_JSON) // Indique qu'on retourne du JSON
public Response getAllOrganisateurs() {
    List<OrganisateurDto> organisateurs = organisateurService.getAllOrganisateurs();

    if (organisateurs.isEmpty()) {
        return Response.ok("{\"message\": \"Aucun organisateur trouvé.\"}").build();
    }

    return Response.ok(organisateurs).build();
}


@POST
@Path("/add")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED) // Accepte les données de formulaire
@Produces(MediaType.APPLICATION_JSON) // Retourne du JSON
public Response createOrganisateur(
        @FormParam("nom") String nom,
        @FormParam("prenom") String prenom,
        @FormParam("email") String email,
        @FormParam("password") String password) {

    // Création d'un organisateur
    OrganisateurDto organisateur = new OrganisateurDto();
    organisateur.setNom(nom);
    organisateur.setPrenom(prenom);
    organisateur.setEmail(email);
    organisateur.setPassword(password);

    // Sauvegarde via le service
    organisateurService.createOrganisateur(organisateur);

    // Retourner une réponse JSON
    return Response.status(Response.Status.CREATED)
                   .entity(organisateur)
                   .build();
}

@PUT
@Path("/update/{id}")
@Consumes(MediaType.APPLICATION_JSON) // Accepte les données en JSON
@Produces(MediaType.APPLICATION_JSON) // Retourne du JSON
public Response updateOrganisateur(@PathParam("id") long id, OrganisateurDto organisateurDetails) {
    if (organisateurDetails == null) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"Données invalides\"}")
                .build();
    }

    // Récupération du l'organisateur existant
    OrganisateurDto organisateurdto = organisateurService.getOrganisateurById(id);
    if (organisateurdto == null) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Organisateur non trouvé\"}")
                .build();
    }

    // Mise à jour des champs (vérifier null)
    if (organisateurDetails.getNom() != null && !organisateurDetails.getNom().isEmpty()) {
        organisateurdto.setNom(organisateurDetails.getNom());
    }
    if (organisateurDetails.getPrenom() != null && !organisateurDetails.getPrenom().isEmpty()) {
        organisateurdto.setPrenom(organisateurDetails.getPrenom());
    }
    if (organisateurDetails.getEmail() != null && !organisateurDetails.getEmail().isEmpty()) {
        organisateurdto.setEmail(organisateurDetails.getEmail());
    }

    if (organisateurDetails.getPassword() != null && !organisateurDetails.getPassword().isEmpty()) {
        organisateurdto.setPassword(organisateurDetails.getPassword());
    }

    // Mise à jour dans la base de données
    organisateurService.updateOrganisateur(id, organisateurdto);

    // Retourner l'organisateur mis à jour
    return Response.ok(organisateurdto).build();
}



@DELETE
@Path("/delete/{id}")
public Response deleteOrganisateur(@PathParam("id") Long id, @Context UriInfo uriInfo) {
    try {
        organisateurService.deleteOrganisateur(id);
        return Response.ok().entity("organisateur supprimé avec succès.").build();
    } catch (IllegalArgumentException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }

}


}
