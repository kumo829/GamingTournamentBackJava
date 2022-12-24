package com.javatutoriales.gaming.users.application.ports.output;

import com.javatutoriales.gaming.users.domain.entities.Member;

import java.util.Optional;
import java.util.stream.Stream;

public interface MembersRegistryOutputPort {
    Stream<Member> getAll();

    Optional<Member> findByUsername(String username);
}
