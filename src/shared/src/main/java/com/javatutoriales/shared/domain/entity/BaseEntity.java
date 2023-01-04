package com.javatutoriales.shared.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity<ID> {
    private final ID id;
}
