package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.io.Serializable;
import java.util.List;
import net.java.dev.cejug.classifieds.server.ejb3.entity.AbstractEntity;

/**
 * @author $Author: felipegaucho $
 * @version $Rev: 309 $ ($Date: 2008-06-18 13:21:41 +0200 (Wed, 18 Jun 2008) $) *
 * @param <T> the entity used in the persistence operations.
 */
public interface EntityFacade<T extends AbstractEntity> {

    /**
     * <strong>C</strong><font color='gray'>rud</font> operation - inserts a
     * new entity in the database.
     * @param entity The entity to be included in the database.
     * @throws Exception database exception, or incompatible entity.
     */
    void create(T entity) throws Exception;

    List<T> readAll(Class<T> entityClass) throws Exception;

    T read(Class<T> entityClass, Serializable primaryKey) throws Exception;

    /**
     * <font color='gray'>cru</font><strong>D</strong> operation - removes an
     * entity from the database.
     * @param entity The entity to be excluded from the database.
     * @throws Exception database exception, or incompatible entity.
     */
    void delete(T entity) throws Exception;

    void delete(Class<T> entityClass, Serializable primaryKey) throws Exception;

    void deleteAll(List<T> entities) throws Exception;

    /**
     * <font color='gray'>cr</font><strong>U</strong><font color='gray'>d</font>
     * operation - merges the entity with the database one.
     * @param entity The entity to be merged in the database.
     * @return the updated entity, with latest data.
     * @throws Exception database exception, or incompatible entity.
     */
    public T update(T entity) throws Exception;
}
