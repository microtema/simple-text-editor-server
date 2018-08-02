package de.seven.fate.texteditor.converter;

import de.seven.fate.texteditor.vo.ErrorMessage;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import static de.seven.fate.texteditor.constants.Constants.MAY_NOT_BE_NULL;

@Component
public class ObjectError2ErrorMessageConverter implements DefaultConverter<ObjectError, ErrorMessage> {

    @Override
    public void update(ObjectError source, ErrorMessage target) {
        Validate.notNull(source, MAY_NOT_BE_NULL, "source");
        Validate.notNull(target, MAY_NOT_BE_NULL, "target");

        FieldError fieldError = (FieldError) source;

        target.setObjectName(fieldError.getObjectName());
        target.setDefaultMessage(fieldError.getDefaultMessage());
        target.setPropertyName(fieldError.getField());
    }

    @Override
    public ErrorMessage createInstance() {
        return new ErrorMessage();
    }
}
