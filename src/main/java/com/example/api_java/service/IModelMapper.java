package com.example.api_java.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface IModelMapper<D, E> {
    E createFromD(D dto);

    D createFromE(E entity);

    E updateEntity(E entity, D dto);

    default List<D> createFromEntities(final Collection<E> entities) { // collection: cho phép nhiều kiểu dư liệu, cho phép kế thừa
        return entities.stream() //Stream đại diện cho một chuỗi các giá trị và phục vụ nhiều chức năng tổng hợp
                .map(this::createFromE)
                .collect(Collectors.toList());
    }
}