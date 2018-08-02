package de.seven.fate.texteditor.vo;

import lombok.Data;

@Data
public class ErrorMessage {

    private String objectName;
    private String propertyName;
    private String defaultMessage;
}
