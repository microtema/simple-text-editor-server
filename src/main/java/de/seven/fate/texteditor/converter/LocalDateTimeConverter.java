package de.seven.fate.texteditor.converter;

import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * autoApply = true will be auto activated so the domain stay clean
 */
@Component
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDateTime localDateTime) {

        if (localDateTime == null) {
            return null;
        }

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date date) {

        if (date == null) {
            return null;
        }

        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
