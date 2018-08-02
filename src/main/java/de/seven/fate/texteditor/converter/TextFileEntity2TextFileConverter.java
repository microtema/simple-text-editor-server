package de.seven.fate.texteditor.converter;

import de.seven.fate.texteditor.entity.TextFileEntity;
import de.seven.fate.texteditor.vo.TextFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static de.seven.fate.texteditor.constants.Constants.MAY_NOT_BE_NULL;

@Component
public class TextFileEntity2TextFileConverter implements DefaultConverter<TextFileEntity, TextFile> {

    private final LocalDateTimeConverter localDateConverter;

    public TextFileEntity2TextFileConverter(LocalDateTimeConverter localDateTimeConverter) {
        this.localDateConverter = localDateTimeConverter;
    }

    @Override
    public void update(TextFileEntity source, TextFile target) {
        Validate.notNull(source, MAY_NOT_BE_NULL, "source");
        Validate.notNull(target, MAY_NOT_BE_NULL, "target");

        target.setId(source.getId());
        target.setFileName(source.getFileName());
        target.setContent(source.getContent());
        target.setSize(FileUtils.byteCountToDisplaySize(Optional.ofNullable(source.getSize()).orElse(0L)));

        target.setCreatedDate(localDateConverter.convertToDatabaseColumn(source.getCreatedDate()));
        target.setLastModifiedDate(localDateConverter.convertToDatabaseColumn(source.getLastModifiedDate()));
    }

    @Override
    public TextFile createInstance() {
        return new TextFile();
    }
}
