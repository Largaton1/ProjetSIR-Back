package fr.istic.taa.jaxrs;
import fr.istic.taa.jaxrs.dao.*;
import fr.istic.taa.jaxrs.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class JpaTest {


	private EntityManager manager;

	/* public JpaTest(EntityManager manager) {
		this.manager = manager;
	} */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* 	EntityManager manager = EntityManagerHelper.getEntityManager();

		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin(); */

		
		try {

			// Administrateur admin = new Administrateur();
			// AdministrateurDao daoAdmin = new AdministrateurDao();
            // admin.setNom("Jean");
			// admin.setEmail("jean@example.com");
			// admin.setPassword("12345678");
			// daoAdmin.save(admin);
            

            // Organisateur organisateur = new Organisateur();
			// OrganisateurDao daoOrganisateur = new OrganisateurDao();
            // organisateur.setNom("Organisateur1");
			// organisateur.setEmail("admin@example.com");
			// organisateur.setPassword("12345678");
            // daoOrganisateur.save(organisateur);
		


			Client client = new Client();
			ClientDao daoClient = new ClientDao();
			client.setNom("Olivier");
			client.setPrenom("Martin");
			client.setEmail("mat@gmail.com");
			client.setPassword("12345678");
			daoClient.save(client);
			// Evenement event = new Evenement();
			// EvenementDao dao = new EvenementDao();
			// event.setNomEvent("Concert Geant");
			// event.setCapacite(10);
			// event.setAdministrateur(admin);
            // event.setOrganisateur(organisateur);
			// dao.save(event);




			/* Administrateur admin = new Administrateur();
			Organisateur organisateur = new Organisateur();
			Client client = new Client();
			Evenement event = new Evenement();
			Ticket ticket = new Ticket(); */
			/* EntityManagerHelper.getEntityManager().persist(admin);
			EntityManagerHelper.getEntityManager().persist(organisateur);
			EntityManagerHelper.getEntityManager().persist(event);
			EntityManagerHelper.getEntityManager().persist(ticket);
			EntityManagerHelper.getEntityManager().persist(client); */

			// TODO create and persist entity
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* tx.commit();

			
   	 manager.close();
		EntityManagerHelper.closeEntityManagerFactory();
		System.out.println(".. done"); */
	}




}
