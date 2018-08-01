package de.seven.fate.texteditor.exception;

public class NoSuchEntityException extends RuntimeException {

    public NoSuchEntityException(String message, String entityName) {
        super(String.format(message, entityName));
    }
}
