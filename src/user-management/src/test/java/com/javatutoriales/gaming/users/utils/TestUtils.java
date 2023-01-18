package com.javatutoriales.gaming.users.utils;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtils {
    public static final String HOST = "http://localhost";
    public static final String SECURE_PASSWORD = "z5Z5gUc6Y!w>";
    public static final String UUID_PATTERN = "[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}";
    public static final String BCRYPT_PATTERN = "\\A\\$2(a|y|b)?\\$\\d\\d\\$[./0-9A-Za-z]{53}";
}
