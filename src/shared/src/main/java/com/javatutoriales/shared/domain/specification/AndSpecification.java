package com.javatutoriales.shared.domain.specification;

import com.javatutoriales.shared.domain.exception.SpecificationException;

public class AndSpecification<T> implements Specification<T> {
    private Specification<T> spec1;
    private Specification<T> spec2;

    public AndSpecification(final Specification<T> spec1,
                            final Specification<T> spec2) {
        this.spec1 = spec1;
        this.spec2 = spec2;
    }

    public boolean isSatisfiedBy(final T t) {
        return spec1.isSatisfiedBy(t) &&
                spec2.isSatisfiedBy(t);
    }

    @Override
    public void check(T t) throws SpecificationException {
        // Not necessary to validate here
    }
}
