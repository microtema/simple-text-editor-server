package de.seven.fate.texteditor.repository;

import de.seven.fate.texteditor.entity.BaseEntity;
import de.seven.fate.texteditor.entity.EntityProperty;
import de.seven.fate.texteditor.exception.NoSuchEntityException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static de.seven.fate.texteditor.constants.Constants.MAY_NOT_BE_NULL;
import static de.seven.fate.texteditor.constants.Constants.NO_SUCH_ENTITY;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Central repository for generic CRUD operations and additional methods
 * on a repository for a specific type.
 *
 * @param <T> generic type for Entity
 * @param <I> generic type for id
 */
@NoRepositoryBean
public interface ExtendedCrudRepository<T extends BaseEntity<I>, I extends Serializable> extends JpaRepository<T, I>, CrudRepository<T, I>, JpaSpecificationExecutor<T> {

    /**
     * @param entity may not be null
     * @return saved or update a given entity.
     */
    default T saveOrUpdate(T entity) {
        notNull(entity, MAY_NOT_BE_NULL, "entity");

        T recent = findOne(entity);

        if (recent == null) {
            return save(entity);
        }

        List<EntityProperty> updatedProperties = update(recent, entity);

        if (CollectionUtils.isEmpty(updatedProperties)) {
            return recent;
        }

        return save(recent);
    }


    /**
     * @param entity may not be null
     * @return Retrieves an entity by its id.
     */
    default T findOne(T entity) {
        notNull(entity, MAY_NOT_BE_NULL, "entity");

        if (entity.getId() == null) {
            return null;
        }

        return findOne(entity.getId());
    }

    /**
     * @param entityId may not be null
     * @return Retrieves an entity by its id.
     */
    default T findOne(I entityId) {
        notNull(entityId, MAY_NOT_BE_NULL, "entityId");

        return findById(entityId).orElse(null);
    }

    /**
     * @param entity may not be null
     * @return Retrieves an entity by entity.
     */
    default T getOne(T entity) {
        notNull(entity, MAY_NOT_BE_NULL, "entity");

        T recent = findOne(entity);

        if (recent != null) {
            return recent;
        }

        throw new NoSuchEntityException(NO_SUCH_ENTITY, getEntityName());
    }

    /**
     * @param entityId may not be null
     * @return Retrieves an entity by its id.
     */
    default T getOne(I entityId) {
        notNull(entityId, MAY_NOT_BE_NULL, "primaryId");

        T recent = findOne(entityId);

        if (recent != null) {
            return recent;
        }

        throw new NoSuchEntityException(NO_SUCH_ENTITY, getEntityType().getSimpleName());
    }

    /**
     * @param entity may not be null
     * @return true if entity exist in DB
     */
    default boolean exists(T entity) {
        notNull(entity, MAY_NOT_BE_NULL, "entity");

        return findOne(entity) != null;
    }


    Class<T> getEntityType();

    /**
     * @param recent  may not be null
     * @param current may not be null
     */
    default List<EntityProperty> update(T recent, T current) {
        return Collections.emptyList();
    }

    default String getEntityName() {
        return getEntityType().getSimpleName();
    }
}
