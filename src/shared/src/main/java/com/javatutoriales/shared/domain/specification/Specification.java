package com.javatutoriales.shared.domain.specification;

import com.javatutoriales.shared.domain.exception.SpecificationException;

public interface Specification<T> {
    boolean isSatisfiedBy(T t);

    void check(T t) throws SpecificationException;

    default Specification<T> and(final Specification<T> specification) {
        return new AndSpecification<>(this, specification);
    }
}
