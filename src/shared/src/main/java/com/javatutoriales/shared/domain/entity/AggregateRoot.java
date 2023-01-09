package com.javatutoriales.shared.domain.entity;

public abstract class AggregateRoot<Id> extends BaseEntity<Id> {
    protected AggregateRoot(Id id) {
        super(id);
    }
}
