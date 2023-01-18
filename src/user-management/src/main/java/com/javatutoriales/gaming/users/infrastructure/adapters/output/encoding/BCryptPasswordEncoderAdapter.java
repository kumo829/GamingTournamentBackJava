package com.javatutoriales.gaming.users.infrastructure.adapters.output.encoding;

import com.javatutoriales.gaming.users.application.ports.output.PasswordEncoderOutputPort;
import lombok.NonNull;
import org.bouncycastle.crypto.generators.OpenBSDBCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class BCryptPasswordEncoderAdapter implements PasswordEncoderOutputPort {
    private static final int BCRYPT_SALT_LEN = 16;

    @Value("${app.credentials.encoder.strength}")
    private int strength;

    @Value("${app.credentials.encoder.version}")
    private String version;

    private final SecureRandom random = new SecureRandom();

    @Override
    public String encode(@NonNull String password) {
        byte[] randomSalt = new byte[BCRYPT_SALT_LEN];

        random.nextBytes(randomSalt);
        return OpenBSDBCrypt.generate(version, password.toCharArray(), randomSalt, strength);
    }
}
