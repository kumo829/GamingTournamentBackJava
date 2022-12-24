package com.javatutoriales.shared.domain.specification;

import com.javatutoriales.shared.domain.exception.SpecificationException;

public abstract class AbstractSpecification<T> implements Specification<T> {
    public abstract void check(T t) throws SpecificationException;

}
