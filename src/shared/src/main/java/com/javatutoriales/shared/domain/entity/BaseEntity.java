package com.javatutoriales.shared.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity<ID> {
    @NotNull
    private final ID id;
}
