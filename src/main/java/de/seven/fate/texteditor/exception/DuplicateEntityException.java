package de.seven.fate.texteditor.exception;

public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String message, String entityName) {
        super(String.format(message, entityName));
    }
}
