package com.javatutoriales.shared.domain.entity;

import lombok.Data;

@Data
public abstract class BaseEntity<ID> {
    private final ID id;
}
