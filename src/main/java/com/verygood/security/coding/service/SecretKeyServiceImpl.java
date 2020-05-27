package com.verygood.security.coding.service;

import com.verygood.security.coding.api.ISecretKeyService;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SecretKeyServiceImpl implements ISecretKeyService {

    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final String SHA1PRNG_ALGORITHM = "SHA1PRNG";
    static final int KEY_SIZE = 24;
    private byte[] encryptionKeyInBytes;
    private String encryptionKey;

    SecretKeyServiceImpl(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    @Override
    public byte[] getKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (encryptionKeyInBytes == null) {
            encryptionKeyInBytes = generateKey();
        }
        return encryptionKeyInBytes;
    }

    private byte[] generateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] chars = encryptionKey.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, KEY_SIZE * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance(SHA1PRNG_ALGORITHM);
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
}
