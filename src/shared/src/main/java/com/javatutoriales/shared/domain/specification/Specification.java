package com.javatutoriales.shared.domain.specification;

public interface Specification<T> {
    boolean isSatisfiedBy(T t);

    String message();

    default Specification<T> and(final Specification<T> specification) {
        return new AndSpecification<>(this, specification);
    }
}
