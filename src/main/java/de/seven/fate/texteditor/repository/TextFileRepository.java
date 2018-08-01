package de.seven.fate.texteditor.repository;

import de.seven.fate.texteditor.entity.TextFileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface TextFileRepository extends CrudRepository<TextFileEntity, Long> {

    /**
     * @param fileName may not be null or empty
     * @return List of TextFileEntity
     */
    List<TextFileEntity> findAllByFileNameContainingIgnoreCase(@NotNull String fileName);
}
