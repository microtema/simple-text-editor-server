package de.seven.fate.texteditor.repository;

import de.seven.fate.texteditor.entity.EntityProperty;
import de.seven.fate.texteditor.entity.TextFileEntity;
import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static de.seven.fate.texteditor.constants.Constants.MAY_NOT_BE_NULL;

@Repository
public interface TextFileRepository extends ExtendedCrudRepository<TextFileEntity, Long> {

    /**
     * @param fileName may not be null or empty
     * @return List of TextFileEntity
     */
    List<TextFileEntity> findAllByFileNameContainingIgnoreCase(@NotNull String fileName);

    default List<TextFileEntity> findAllSorted() {

        return findAll(new Sort(Sort.Direction.ASC, "fileName"));
    }

    @Override
    default List<EntityProperty> update(TextFileEntity recent, TextFileEntity current) {
        Validate.notNull(recent, MAY_NOT_BE_NULL, "recent");
        Validate.notNull(current, MAY_NOT_BE_NULL, "current");

        List<EntityProperty> properties = new ArrayList<>();

        if (Objects.equals(recent.getFileName(), current.getFileName())) {

            properties.add(new EntityProperty<>("fileName", current.getFileName(), recent.getFileName()));

            recent.setFileName(current.getFileName());
        }

        if (Objects.equals(recent.getContent(), current.getContent())) {

            properties.add(new EntityProperty<>("content", current.getContent(), recent.getContent()));

            recent.setContent(current.getContent());
        }

        return properties;
    }

    @Override
    default Class<TextFileEntity> getEntityType() {

        return TextFileEntity.class;
    }
}
