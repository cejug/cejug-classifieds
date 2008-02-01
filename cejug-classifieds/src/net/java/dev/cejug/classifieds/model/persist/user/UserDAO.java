package net.java.dev.cejug.classifieds.model.persist.user;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import net.java.dev.cejug.classifieds.model.entitys.User;

/**
 *
 * Class what represents the design pattern DAO for User.
 * 
 * @author Rafael Carneiro [rafaelcarneirob@gmail.com]
 * @since 02/01/2008
 */

public class UserDAO {

	private EntityManagerFactory entityManagerFactory = null;
	
	private EntityManager entityManager = null;
	
	//TODO Modify in the future the way to obtain these objects.
	/**
	 * Constructor to initialize the objects to configure the JPA.
	 */
	public UserDAO() {
		entityManagerFactory = Persistence.createEntityManagerFactory("cejug-classifieds");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	/**
	 * Method to persist a user in data base.
	 * 
	 * @param user - The entity User
	 */
	public void createUser(User user) {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		
		try {
			entityTransaction.begin();
			
			entityManager.persist(user);
			
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
			
			//TODO Uses AOP or LOG here.
		} finally {
			entityManager.close();
		}
		
		
	}
	
	
}
