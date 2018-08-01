package de.seven.fate.texteditor.converter;

import de.seven.fate.model.builder.util.FieldInjectionUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class LocalDateTimeConverterTest {

    @Autowired
    LocalDateTimeConverter sut;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void convertToDatabaseColumn() {

        LocalDateTime localDateTime = LocalDateTime.now();

        Date date = sut.convertToDatabaseColumn(localDateTime);

        assertEquals(sut.convertToEntityAttribute(date), localDateTime);
    }

    @Test
    public void convertToEntityAttribute() {

        Date date = new Date();

        LocalDateTime localDateTime = sut.convertToEntityAttribute(date);

        assertEquals(sut.convertToDatabaseColumn(localDateTime), date);
    }
}