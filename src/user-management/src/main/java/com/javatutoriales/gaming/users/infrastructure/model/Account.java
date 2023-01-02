package com.javatutoriales.gaming.users.infrastructure.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "accounts")
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Account {
    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String email;
}
