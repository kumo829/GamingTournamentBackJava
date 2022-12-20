package com.javatutoriales.gaming.auth.domain.entities;

import com.javatutoriales.gaming.auth.domain.valueobjects.Credentials;
import com.javatutoriales.gaming.auth.domain.valueobjects.MemberId;
import com.javatutoriales.shared.domain.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
@Builder
public class Member extends BaseEntity<MemberId> {
    private final Credentials credentials;
    @NotBlank
    private final String email;
}
