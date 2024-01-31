package org.example.gradingspring.configurations;

import org.example.service.PasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyOwnPasswordEncoder implements PasswordEncoder {

    private final PasswordService passwordService;

    public MyOwnPasswordEncoder(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordService.hashPassword(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordService.validatePassword(rawPassword.toString(), encodedPassword);
    }
}
