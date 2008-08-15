package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import net.java.dev.cejug.classifieds.server.ejb3.entity.AbstractEntity;

/**
 * @author $Author: felipegaucho $
 * @version $Rev: 309 $ ($Date: 2008-06-18 13:21:41 +0200 (Wed, 18 Jun 2008) $)
 * *
 * @see CRUDEntityFacade
 */
public interface EntityFacade<T extends AbstractEntity> {

  /**
   * <strong>C</strong><font color='gray'>rud</font> operation - inserts a new
   * entity in the database.
   * 
   * @param entity The entity to be included in the database.
   * @throws Exception database exception, or incompatible entity.
   */
  void create(T entity) throws EntityExistsException, IllegalStateException,
      IllegalArgumentException, TransactionRequiredException;

  /**
   * <font color='gray'>C</font><strong>R</strong><font color='gray'>ud</font>
   * operation - inserts a new entity in the database. Read all records
   * available on database for a certain type of entity.
   * 
   * @param entityClass The entity type.
   * @return A collection of objects of type 'entityClass'
   * @throws IllegalStateException if called for a Java Persistence query
   * language UPDATE or DELETE statement. See:
   * http://java.sun.com/j2se/1.5/docs/api/java/lang/IllegalStateException.html
   * @throws IllegalArgumentException if query string is not valid.
   */
  List<T> readAll(Class<T> entityClass) throws IllegalStateException, IllegalArgumentException;

  /**
   * TODO: missed comments.
   * 
   * @param entityClass
   * @param primaryKey
   * @return
   * @throws Exception
   */
  T read(Class<T> entityClass, Serializable primaryKey) throws IllegalStateException,
      IllegalArgumentException;

  /**
   * <font color='gray'>cru</font><strong>D</strong> operation - removes an
   * entity from the database.
   * 
   * @param entity The entity to be excluded from the database.
   * @throws Exception database exception, or incompatible entity.
   */
  void delete(T entity) throws IllegalStateException, IllegalArgumentException,
      TransactionRequiredException, PersistenceException;

  void delete(Class<T> entityClass, Serializable primaryKey) throws IllegalStateException,
      IllegalArgumentException, TransactionRequiredException, PersistenceException;

  void deleteAll(List<T> entities) throws IllegalStateException, IllegalArgumentException,
      TransactionRequiredException, PersistenceException;

  /**
   * <font color='gray'>cr</font><strong>U</strong><font color='gray'>d</font>
   * operation - merges the entity with the database one.
   * 
   * @param entity The entity to be merged in the database.
   * @return the updated entity, with latest data.
   * @throws Exception database exception, or incompatible entity.
   */
  public T update(T entity) throws IllegalStateException, IllegalArgumentException,
      TransactionRequiredException;
}
