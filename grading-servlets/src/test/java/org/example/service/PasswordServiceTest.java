package org.example.service;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordServiceTest
{
    @Test
    void testEncryptionAndDecryption() {
        PasswordService passwordService = new PasswordService();
        String originalPassword = "123";
        String generatedSecuredPasswordHash = passwordService.hashPassword(originalPassword);
        System.out.println(generatedSecuredPasswordHash);
        boolean matched = passwordService.validatePassword("123", generatedSecuredPasswordHash);
        assertTrue(matched);
        matched = passwordService.validatePassword("1234", generatedSecuredPasswordHash);
        assertFalse(matched);
    }
}