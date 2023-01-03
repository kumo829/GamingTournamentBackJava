package com.javatutoriales.shared.domain.entity;

public abstract class AggregateRoot<ID> extends BaseEntity<ID> {
    public AggregateRoot(ID id) {
        super(id);
    }
}
