package de.seven.fate.texteditor.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"fileName", "content"}, callSuper = false)
@Table(name = "TEXT_FILE", uniqueConstraints = {@UniqueConstraint(columnNames = {"fileName"})})
public class TextFileEntity extends BaseAuditEntity<Long> {

    @NotNull
    @OrderBy("fileName ASC")
    private String fileName;

    @NotNull
    @Column(columnDefinition = "Text")
    private String content;

    private Long size;
}
