package com.javatutoriales.gaming.auth.domain.entities;

import com.javatutoriales.gaming.auth.domain.valueobjects.MemberId;
import com.javatutoriales.shared.domain.entity.BaseEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Member extends BaseEntity<MemberId> {
    @NotEmpty
    private final String firstName;
    @NotEmpty
    private final String lastName;
    @NotEmpty
    private final String email;
}
