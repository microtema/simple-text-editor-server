package de.seven.fate.texteditor.converter;

import de.seven.fate.model.builder.util.FieldInjectionUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class LocalDateConverterTest {

    @Autowired
    LocalDateConverter sut;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void convertToDatabaseColumn() {

        LocalDate now = LocalDate.now();

        Date date = sut.convertToDatabaseColumn(now);

        assertEquals(sut.convertToEntityAttribute(date), now);
    }

    @Test
    public void convertToEntityAttribute() throws Exception {

        Date date = DateUtils.parseDate("2018-08-2", "yyyy-MM-dd");

        LocalDate localDateTime = sut.convertToEntityAttribute(date);

        assertEquals(sut.convertToDatabaseColumn(localDateTime), date);
    }
}