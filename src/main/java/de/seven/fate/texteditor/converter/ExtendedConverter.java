package de.seven.fate.texteditor.converter;

import org.apache.commons.lang3.Validate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface ExtendedConverter<S, T> extends Converter<S, T> {

    default T convert(S source) {
        Validate.notNull(source);

        T target = createInstance();

        update(source, target);

        return target;
    }

    default void update(S source, T target) {

    }

    default T createInstance() {

        return null;
    }

    default List<T> convertToList(Collection<S> sources) {

        if (CollectionUtils.isEmpty(sources)) {

            return Collections.emptyList();
        }

        return sources.stream().map(this::convert).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    default Set<T> convertToSet(Collection<S> sources) {

        if (CollectionUtils.isEmpty(sources)) {

            return Collections.emptySet();
        }

        return sources.stream().map(this::convert).collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
    }
}
