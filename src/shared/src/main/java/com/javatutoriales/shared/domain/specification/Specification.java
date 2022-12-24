package com.javatutoriales.shared.domain.specification;

public interface Specification<T> {
    boolean isSatisfiedBy(T t);

    default Specification<T> and(final Specification<T> specification) {
        return new AndSpecification<>(this, specification);
    }
}
