package net.java.dev.cejug.classifieds.model.dao;

/**
 * This is the most important piece of code regarding persistence, because it
 * decouples the persistence of the underneath technologies like Hibernate or
 * JPA. The persistence should be always called through this interface and never
 * directly using technology specific features. This way, we can modify the
 * mechanism used to persist the data without loosing the database schema o
 * persistence design.
 * 
 * This interface is the isolation between the DAO features and the underneath
 * technologies. Never expose technology dependent features, since technologies
 * should not be part of the system design ;)
 * 
 * @see <a
 *      href='http://en.wikipedia.org/wiki/Create%2C_read%2C_update_and_delete'>CRUD</a>
 * @author Felipe Gaúcho
 * 
 * @param <ClassifiedsBean>
 *            Generic type of Bean used to persist pojos in the database.
 */
public interface ClassifiedsDao<Pojo> {
	/**
	 * Add an entity to the database.
	 * 
	 * @param bean
	 *            the POJO containning the data to be persisted.
	 * @throws Exception
	 */
	void create(Pojo pojo) throws Exception;

	/**
	 * Add an entity to the database.
	 * 
	 * @param bean
	 *            the POJO containning the data to be persisted.
	 * @throws Exception
	 */
	void read(Pojo pojo) throws Exception;

	/**
	 * Add an entity to the database.
	 * 
	 * @param bean
	 *            the POJO containning the data to be persisted.
	 * @throws Exception
	 */
	void update(Pojo pojo) throws Exception;

	/**
	 * Delete an entity record from database.
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	void delete(Pojo pojo) throws Exception;
}