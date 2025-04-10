package fr.istic.taa.jaxrs.rest;

import java.net.URI;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import fr.istic.taa.jaxrs.dao.*;
import fr.istic.taa.jaxrs.domain.*;
import fr.istic.taa.jaxrs.dto.*;
import fr.istic.taa.jaxrs.service.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
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


@Path("/evenement")
@Produces({"application/json"})
public class EvenementResource {

  EvenementService evenementService = new EvenementService();



@GET
@Path("/{id}")
@Produces(MediaType.APPLICATION_JSON) // On indique que la réponse est en HTML
public Response getEvenementById(@PathParam("id") Long id) {
    EvenementDto evenement = evenementService.getEvenementById(id);

    if (evenement == null) {
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"message\": \"Aucun evenement trouvé.\"}")
                       .build();
    }

    return Response.ok(evenement).build();
}


@GET
@Path("/")
@Produces(MediaType.APPLICATION_JSON) // Indique qu'on retourne du JSON
public Response getAllEvenements() {
    List<EvenementDto> evenements = evenementService.getAllEvenements();

    if (evenements.isEmpty()) {
        return Response.ok("{\"message\": \"Aucun evenement trouvé.\"}").build();
    }

    return Response.ok(evenements).build();
}


@POST
@Path("/add")
/* @Consumes(MediaType.APPLICATION_JSON) // Accepte les données de formulaire
@Produces(MediaType.APPLICATION_JSON) // Retourne du JSON
public Response createEvenement(
    EvenementDto evenement) {

    

    // Sauvegarde via le service
    evenementService.createEvenement(evenement);

    // Retourner une réponse JSON
    return Response.status(Response.Status.CREATED)
                   .entity(evenement)
                   .build();
} */

@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
@Produces(MediaType.APPLICATION_JSON)
@io.swagger.v3.oas.annotations.parameters.RequestBody(
    content = @Content(
        mediaType = "application/x-www-form-urlencoded",
        encoding = {
            @Encoding(name = "nomEvent", contentType = "text/plain; charset=UTF-8"),
            @Encoding(name = "lieu", contentType = "text/plain; charset=UTF-8"),
            @Encoding(name = "description", contentType = "text/plain; charset=UTF-8"),
          
        }
    )
)
public Response createEvenement(
    @FormParam("nomEvent") String nomEvent,
    @FormParam("date") Date dateStr,  
    @FormParam("lieu") String lieu,
    @FormParam("description") String description,
    @FormParam("capacite") int capacite,
    @FormParam("statut") String statutStr, // 📌 Reçu en tant que String
    @FormParam("organisateurId") Long organisateurId,
    @FormParam("administrateurId") Long administrateurId
    ) {

    // 📌 Vérifier si l'organisateur existe
    OrganisateurDto organisateur = new OrganisateurService().getOrganisateurById(organisateurId);
    if (organisateur == null) {
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("Organisateur non trouvé pour l'ID fourni.")
                       .build();
    }
    
    AdministrateurDto administrateur = new AdministrateurService().getAdministrateurById(administrateurId);
    if (administrateur == null) {
        return Response.status(Response.Status.NOT_FOUND)
                    .entity("Administrateur non trouvé pour l'ID fourni.")
                    .build();
    }

    // 📌 Convertir la date (format "yyyy-MM-dd")
    Date date;
    try {
        date = Date.valueOf(dateStr.toString()); // Utilisation de java.sql.Date pour convertir directement
    } catch (IllegalArgumentException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                       .entity("Format de date invalide, attendu : yyyy-MM-dd")
                       .build();
    }

    // 📌 Convertir statutEvent (Enum)
    statutEvent statut;
    try {
        statut = statutEvent.valueOf(statutStr); // 🔥 Conversion String → Enum
    } catch (IllegalArgumentException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                       .entity("Statut invalide, valeurs acceptées : EnCours, Annule, Termine")
                       .build();
    }

    // 📌 Créer l'objet Evenement
    EvenementDto evenementDto = new EvenementDto();
    evenementDto.setNomEvent(nomEvent);
    evenementDto.setDate(date);
    evenementDto.setLieu(lieu);
    evenementDto.setDescription(description);
    evenementDto.setCapacite(capacite);
    evenementDto.setStatut(statut); // 📌 Associer le statut
   evenementDto.setOrganisateur(organisateur); // 📌 Associer l'organisateur
   evenementDto.setAdministrateur(administrateur);

    // 📌 Sauvegarde via le service
    evenementService.createEvenement(evenementDto);

    return Response.status(Response.Status.CREATED)
                   .entity(evenementDto)
                   .build();
}

@PUT
@Path("/update/{id}")
@Consumes(MediaType.APPLICATION_JSON) // Accepte les données en JSON
@Produces(MediaType.APPLICATION_JSON) // Retourne du JSON
@io.swagger.v3.oas.annotations.parameters.RequestBody(
    content = @Content(
        mediaType = "application/x-www-form-urlencoded",
        encoding = {
            @Encoding(name = "nomEvent", contentType = "text/plain; charset=UTF-8"),
            @Encoding(name = "lieu", contentType = "text/plain; charset=UTF-8"),
            @Encoding(name = "description", contentType = "text/plain; charset=UTF-8"),
          
        }
    )
)
public Response updateEvenement(@PathParam("id") long id, EvenementDto evenementDetails) {
    if (evenementDetails == null) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"Données invalides\"}")
                .build();
    }

    // Récupération de l'administrateur existant
    EvenementDto evenementdto = evenementService.getEvenementById(id);
    if (evenementdto == null) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Evenement non trouvé\"}")
                .build();
    }
    // Mise à jour dans la base de données
    evenementService.updateEvenement(id, evenementdto);

    // Retourner l'evenement mis à jour
    return Response.ok(evenementdto).build();
}



@DELETE
@Path("/delete/{id}")
public Response deleteEvenement(@PathParam("id") Long id, @Context UriInfo uriInfo) {
    try {
        evenementService.deleteEvenement(id);
        return Response.ok().entity("Evenement supprimé avec succès.").build();
    } catch (IllegalArgumentException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }

}




}