package ru.otus.spring.kermilov.books;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordTest {
    private static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    void pass() {
        System.out.println(
            passwordEncoder.encode("OtusTeacher")
        );
        assertTrue(true);
    }
}
