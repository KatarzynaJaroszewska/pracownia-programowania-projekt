package com.uam.pracowniaprogramowaniaprojekt.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface AbstractMapper<D,E> {

    D mapToDto(E e);

    E mapToEntity(D d);

    default List<D> mapToDtos(final List<E> list) {
        return list.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    default List<E> mapToEntities(final List<D> list) {
        return list.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

}
