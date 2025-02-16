## Application de Vente de Tickets de Concert en Ligne

# 1 . Etat d'avancement 

Nous avons terminé le TP5 Des servlets aux services Web
 . TP 2: Des servlets aux API Rest documentées avec OpenAPI

 # Ce qui fonctionne :

    . Base de données configurée avec JPA
    . Modèle métier initial avec entités et relations
    . Gestion de l’héritage pour certaines entités (Organisateur, Client, Administrateur qui hérite de la classe Personne)
    . DAO (Data Access Objects) pour la gestion des entités en base
    . La couche DTO( Data Transfer Objects)
    . La couche Service
    . Couche REST avec endpoints de base
    . OpenAPI avec Swagger UI intégrer au projet
    - Pour notre projet nous avons utiliser la connexion à une base mysql
    (jdbc:mysql://localhost:3306/mydatabase;  user:root ; password:"")

    Concernant la couche REST 
     - Pour les classes AdministrateurResource, OrganisateurResource et ClientResource les méthodes
        getById, getAll, Create, update and delete fonctionne bien via le swagger
    -Pour les classe EvenementResource, TicketResource les méthodes 
        getById, getAll, delete fonctionne aussi bien via le swagger

# Ce qui ne marche pas
    
    Concernant la couche REST
    -Pour les classe EvenementResource, TicketResource les méthodes 
        Create et update ne marche pas encore, on n'avait rencontré un problème concernant le LocalDateTime qui n'est pas reconnu pas JSON, du coup nous avons changer le type des dates en Date de Java.util.sql et deplus nous avons ajouté le @JsonFormat(pattern = « yyyy-MM-dd ») sur les méthode de récupération des dates.


# La Prochaine étape

    - Revoir les méthodes create et update des classes EvenementResource et TicketResource 
    - Ensuite revoir notre modèle métier, on veut la faire évoluer en ajoutant les classes PremiumTicket et LastMinuteTicket qui vont hérité de la classe Ticket, ajouter aussi une classe de paiement.
    -Continuer par la suite avec la partie JS TP6 et le TP7

# Démarrage du projet

    -Exécuter le fichier RestServer.java
    -pour besoin de persister des donner dans la base de donner manuellement vous pouver modifier dans le fichier JpaTest.java et l'exécuter 

# Pour accéder à l'api via le swagger 

voici le lien  (http://localhost:8080/api/)

