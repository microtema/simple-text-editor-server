package de.seven.fate.texteditor.entity;

import de.seven.fate.model.builder.annotation.Model;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TextFileEntityTest {

    @Model
    TextFileEntity sut;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void isAnnotatedWithEntity() {

        assertTrue(TextFileEntity.class.isAnnotationPresent(Entity.class));
    }

    @Test
    public void isAnnotatedWithTable() {

        assertTrue(TextFileEntity.class.isAnnotationPresent(Table.class));
    }

    @Test
    public void testTableName() {

        Table annotation = TextFileEntity.class.getAnnotation(Table.class);

        assertEquals("TEXT_FILE", annotation.name());
    }

    @Test
    public void testUniqueConstraints() {

        Table annotation = TextFileEntity.class.getAnnotation(Table.class);

        UniqueConstraint[] uniqueConstraints = annotation.uniqueConstraints();
        assertEquals(1, uniqueConstraints.length);
        assertArrayEquals(new String[]{"fileName"}, uniqueConstraints[0].columnNames());
    }

    @Test
    public void testEquals() {

        TextFileEntity other = new TextFileEntity();

        other.setFileName(sut.getFileName());
        other.setContent(sut.getContent());

        assertEquals(sut, other);
    }

    @Test
    public void testNotEquals() {

        TextFileEntity other = new TextFileEntity();

        other.setFileName(sut.getFileName() + ".changed");
        other.setContent(sut.getContent());

        assertNotEquals(sut, other);
    }

    @Test
    public void testHashCode() {

        TextFileEntity other = new TextFileEntity();

        other.setFileName(sut.getFileName());
        other.setContent(sut.getContent());

        assertEquals(sut.hashCode(), other.hashCode());
    }

    @Test
    public void testHashCodeNotEquals() {

        TextFileEntity other = new TextFileEntity();

        other.setFileName(sut.getFileName() + ".changed");
        other.setContent(sut.getContent());

        assertNotEquals(sut.hashCode(), other.hashCode());
    }
}