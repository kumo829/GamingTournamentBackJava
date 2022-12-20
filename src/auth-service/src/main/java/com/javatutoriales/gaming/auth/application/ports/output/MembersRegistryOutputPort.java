package com.javatutoriales.gaming.auth.application.ports.output;

import com.javatutoriales.gaming.auth.domain.entities.Member;

import java.util.stream.Stream;

public interface MembersRegistryOutputPort {
    Stream<Member> getAll();
}
