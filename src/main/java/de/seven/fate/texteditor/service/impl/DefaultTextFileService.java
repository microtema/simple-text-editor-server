package de.seven.fate.texteditor.service.impl;

import de.seven.fate.texteditor.converter.TextFile2TextFileEntityConverter;
import de.seven.fate.texteditor.converter.TextFileEntity2TextFileConverter;
import de.seven.fate.texteditor.entity.TextFileEntity;
import de.seven.fate.texteditor.repository.TextFileRepository;
import de.seven.fate.texteditor.service.TextFileService;
import de.seven.fate.texteditor.vo.TextFile;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.List;

import static de.seven.fate.texteditor.constants.Constants.DUPLICATE_ENTITY;
import static de.seven.fate.texteditor.constants.Constants.MAY_NOT_BE_EMPTY;
import static de.seven.fate.texteditor.constants.Constants.MAY_NOT_BE_NULL;
import static de.seven.fate.texteditor.constants.Constants.MAY_NOT_EXISTS;

@Log4j2
@Service
public class DefaultTextFileService implements TextFileService {

    private final TextFileRepository textFileRepository;
    private final TextFile2TextFileEntityConverter textFile2TextFileEntityConverter;
    private final TextFileEntity2TextFileConverter textFileEntity2TextFileConverter;

    public DefaultTextFileService(TextFileRepository textFileRepository,
                                  TextFile2TextFileEntityConverter textFile2TextFileEntityConverter,
                                  TextFileEntity2TextFileConverter textFileEntity2TextFileConverter) {
        this.textFileRepository = textFileRepository;
        this.textFile2TextFileEntityConverter = textFile2TextFileEntityConverter;
        this.textFileEntity2TextFileConverter = textFileEntity2TextFileConverter;
    }

    @Override
    public List<TextFile> getTextFiles() {

        return textFileEntity2TextFileConverter.convertToList(textFileRepository.findAllSorted());
    }

    @Override
    public Long createTextFile(TextFile textFile) {
        Validate.notNull(textFile, MAY_NOT_BE_NULL, "textFile");

        TextFileEntity textFileEntity = textFile2TextFileEntityConverter.convert(textFile);

        Validate.isTrue(!textFileRepository.exists(textFileEntity), DUPLICATE_ENTITY, textFile.getFileName());

        Validate.isTrue(!existsTextFile(textFile.getFileName()), DUPLICATE_ENTITY, textFile.getFileName());

        log.debug(() -> "Create new Text File: " + textFile.getFileName());

        return textFileRepository.save(textFileEntity).getId();
    }

    @Override
    public Long updateTextFile(TextFile textFile) {
        Validate.notNull(textFile, MAY_NOT_BE_NULL, "textFile");

        TextFileEntity textFileEntity = textFile2TextFileEntityConverter.convert(textFile);

        Validate.isTrue(textFileRepository.exists(textFileEntity), MAY_NOT_EXISTS, textFile.getFileName());

        log.debug(() -> "Update Text File: " + textFile.getFileName());

        return textFileRepository.saveOrUpdate(textFileEntity).getId();
    }

    @Override
    public TextFile getTextFile(Long id) {
        Validate.notNull(id, MAY_NOT_BE_NULL, "id");

        TextFileEntity textFileEntity = textFileRepository.getOne(id);

        log.debug(() -> "Get Text File by Id: " + id);

        return textFileEntity2TextFileConverter.convert(textFileEntity);
    }

    @Override
    public void deleteTextFile(Long id) {
        Validate.notNull(id, MAY_NOT_BE_NULL, "id");

        log.debug(() -> "Delete Text File by Id: " + id);

        textFileRepository.deleteById(id);
    }

    @Override
    public boolean existsTextFile(String fileName) {
        Validate.notNull(fileName, MAY_NOT_BE_EMPTY, "fileName");

        return !textFileRepository.findAllByFileNameContainingIgnoreCase(fileName).isEmpty();
    }
}
