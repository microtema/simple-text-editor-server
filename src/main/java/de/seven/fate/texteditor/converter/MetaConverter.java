package de.seven.fate.texteditor.converter;

import org.apache.commons.lang3.Validate;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface MetaConverter<S, T, M> extends DefaultConverter<S, T> {

    default T convert(S source, M meta) {
        Validate.notNull(source);

        T target = createInstance();

        update(source, target, meta);

        return target;
    }

    default void update(S source, T target, M meta) {

    }

    default void update(List<S> source, List<T> target, M meta) {
        if (CollectionUtils.isEmpty(source)) {

            return;
        }

        if (CollectionUtils.isEmpty(target)) {

            return;
        }

        Validate.isTrue(source.size() == target.size(), "Size should be same");

        for (int index = 0; index < source.size(); index++) {
            update(source.get(index), target.get(index), meta);
        }

    }

    default List<T> convertToList(Collection<S> sources, M meta) {

        if (CollectionUtils.isEmpty(sources)) {

            return Collections.emptyList();
        }

        return sources.stream().map(it -> convert(it, meta)).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    default Set<T> convertToSet(Collection<S> sources, M meta) {

        if (CollectionUtils.isEmpty(sources)) {

            return Collections.emptySet();
        }

        return sources.stream().map(it -> convert(it, meta)).collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
    }
}
