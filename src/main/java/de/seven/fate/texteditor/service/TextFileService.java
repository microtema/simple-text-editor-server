package de.seven.fate.texteditor.service;

import de.seven.fate.texteditor.vo.TextFile;

import java.util.List;

/**
 * Text File Service that provide all CRUD's operations
 */
public interface TextFileService {

    /**
     * @return List of TextFile
     */
    List<TextFile> getTextFiles();

    /**
     * @param textFile may not be null
     * @return Database id
     */
    Long createTextFile(TextFile textFile);

    /**
     * @param textFile may not be null
     * @return Database id
     */
    Long updateTextFile(TextFile textFile);

    /**
     * @param id may not be null
     * @return TextFile
     * @throws java.util.NoSuchElementException
     */
    TextFile getTextFile(Long id);

    /**
     * Delete TextFile by given id
     *
     * @param id may not be null
     */
    void deleteTextFile(Long id);

    /**
     * Check if TextFile identified by fileName exist
     *
     * @param fileName may not be empty
     * @return Boolean
     */
    boolean existsTextFile(String fileName);
}
