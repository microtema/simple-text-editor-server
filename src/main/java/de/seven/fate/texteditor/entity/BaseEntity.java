package de.seven.fate.texteditor.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * Base Entity with AUTO generation strategy primary key.
 *
 * @param <I> generic type of id
 */
@MappedSuperclass
public class BaseEntity<I extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private I id;

    @Version
    private Long version;

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}