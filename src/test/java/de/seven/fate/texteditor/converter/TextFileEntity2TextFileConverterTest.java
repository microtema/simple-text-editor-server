package de.seven.fate.texteditor.converter;

import de.seven.fate.model.builder.annotation.Model;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import de.seven.fate.texteditor.entity.TextFileEntity;
import de.seven.fate.texteditor.vo.TextFile;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TextFileEntity2TextFileConverterTest {

    @Autowired
    TextFileEntity2TextFileConverter sut;

    @Model
    TextFileEntity entity;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void convert() {

        TextFile model = sut.convert(entity);

        assertNotNull(entity);

        assertEquals(entity.getFileName(), model.getFileName());
        assertEquals(entity.getContent(), model.getContent());
        assertEquals(entity.getSize(), model.getSize());
    }

    @Test
    public void createInstance() {

        assertNotNull(sut.createInstance());
    }
}