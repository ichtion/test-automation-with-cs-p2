package pl.mdziedzic.unittests.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Simple implementation not for production use
 */
public class Repository {

	private static Repository repository;
	private EntityManager em;

	private Repository() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
		em = emf.createEntityManager();
	}

	public static Repository getInstance() {
		if (repository == null) {
			repository = new Repository();
		}

		return repository;
	}

	public void persist(Object object) {
		em.persist(object);
	}

	public void remove(Object object) {
		em.remove(object);
	}

	public void beginTransaction() {
		em.getTransaction().begin();
	}

	public void rollback() {
		em.getTransaction().rollback();
	}
	
	public void commit() {
		em.getTransaction().commit();
	}

}
