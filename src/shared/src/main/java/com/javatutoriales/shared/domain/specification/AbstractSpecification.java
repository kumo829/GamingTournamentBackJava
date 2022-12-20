package com.javatutoriales.shared.domain.specification;

public abstract class AbstractSpecification<T> implements Specification<T> {
    public abstract boolean isSatisfiedBy(T t);
}
