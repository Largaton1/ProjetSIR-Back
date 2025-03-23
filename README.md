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







## Contexte
L'objectif de ce projet est de développer une application web et mobile permettant aux 
utilisateurs d'acheter des tickets de concert en ligne. L'application doit offrir une 
expérience utilisateur fluide, sécurisée et intuitive, tout en permettant aux organisateurs de 
concerts de gérer simplement leurs événements. 

## Objectifs

● Permettre aux utilisateurs de rechercher, consulter et acheter des tickets de 
concert. 
● Offrir une plateforme de gestion pour les organisateurs d'événements. 
● Assurer la sécurité des transactions et des données utilisateurs. 
● Proposer une interface responsive et accessible sur un navigateur web. 

## Fonctionnalités pour les utilisateurs

### Recherche et Consultation des Concerts : 
  ○ Recherche de concerts par artiste, lieu, date, genre musical. 
  ○ Affichage des détails de l'événement (date, lieu, prix, description). 
  ○ Filtres et tris (par prix, date, popularité). 
### Achat de Tickets : 
  ○ Sélection des places (si applicable). 
  ○ Envoi du ticket par email ou téléchargement direct. 
  ○ Option d'annulation et de remboursement (selon les conditions de 
l'organisateur). 
### Gestion des Tickets : 
  ○ Accès à un espace personnel pour consulter les tickets achetés. 
  ○ Possibilité de transférer un ticket à un autre utilisateur. 
### Notifications : 
  ○ Alertes pour les nouveaux concerts, rappels d'événements, et offres 
spéciales.


## Fonctionnalités pour les organisateurs
### Gestion des Événements : 
  ○ Création, modification et suppression d'événements. 
  ○ Ajout de détails (date, lieu, description, prix, capacité). 
  ○ Gestion des stocks de tickets. 
### Statistiques et Rapports : 
  ○ Suivi des ventes en temps réel. 
  ○ Rapports sur les performances des événements. 
### Communication : 
  ○ Envoi de notifications aux acheteurs de tickets (changements d'horaire, 
annulations, etc.). 

## Fonctionnalités administrateur
### Gestion des Événements : 
  ○ Validation des événements créés par les organisateurs. 
  ○ Modération des contenus.

## Exigences techniques

Plateformes 
### Web : Compatibilité avec les navigateurs modernes (Chrome, Firefox, Safari, Edge). 
Technologies 
### Front-end : HTML5, CSS3, JavaScript (React.js ou Angular). 
### Back-end : Java (JPA) 
### Base de données : MySQL, PostgreSQL. 
### Paiement : Intégration d'API de paiement (Stripe, PayPal, etc.) (optionelle ;). 


