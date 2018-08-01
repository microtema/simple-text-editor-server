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

public class TextFile2TextFileEntityConverterTest {

    @Autowired
    TextFile2TextFileEntityConverter sut;

    @Model
    TextFile model;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void convert() {

        TextFileEntity entity = sut.convert(model);

        assertNotNull(entity);

        assertEquals(model.getFileName(), entity.getFileName());
        assertEquals(model.getContent(), entity.getContent());
        assertEquals(model.getSize(), entity.getSize());
    }

    @Test
    public void createInstance() {

        assertNotNull(sut.createInstance());
    }
}