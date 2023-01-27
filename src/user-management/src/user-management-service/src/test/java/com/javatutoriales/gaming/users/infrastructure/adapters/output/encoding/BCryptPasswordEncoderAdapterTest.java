package com.javatutoriales.gaming.users.infrastructure.adapters.output.encoding;

import com.javatutoriales.gaming.users.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class BCryptPasswordEncoderAdapterTest {
    @Test
    void givenABcryptPasswordEncoder_whenThePasswordIsEncoded_thenTheEncodedPasswordShouldHabeBCryptFormat() {
        BCryptPasswordEncoderAdapter passwordEncoder = new BCryptPasswordEncoderAdapter();


        ReflectionTestUtils.setField(passwordEncoder, "strength", 10);
        ReflectionTestUtils.setField(passwordEncoder, "version", "2y");

        String encodedPassword = passwordEncoder.encode("ABC1234@)#(.");

        Assertions.assertThat(encodedPassword).matches(TestUtils.BCRYPT_PATTERN);
    }
}