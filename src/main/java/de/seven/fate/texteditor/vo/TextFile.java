package de.seven.fate.texteditor.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Value Object
 */
@Data
public class TextFile {

    // May be null
    private Long id;

    @NotNull
    private String fileName;

    @NotNull
    private String content;

    private String size;

    private Date createdDate;

    private Date lastModifiedDate;
}
