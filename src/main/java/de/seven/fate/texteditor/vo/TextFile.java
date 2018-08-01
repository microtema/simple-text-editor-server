package de.seven.fate.texteditor.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TextFile {

    @NotNull
    private String fileName;

    @NotNull
    private String content;

    private Long size;
}
