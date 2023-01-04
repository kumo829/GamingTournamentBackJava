package com.javatutoriales.gaming.users.infrastructure.model;

import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@EqualsAndHashCode
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Account {
    @Id
    @NonNull
    private UUID id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;
    @NonNull
    private String email;

    @NonNull
    @Column(updatable = false)
    private String username;

    @NonNull
    private String password;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Profile profile;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
