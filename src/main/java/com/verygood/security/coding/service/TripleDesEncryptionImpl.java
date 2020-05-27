package com.verygood.security.coding.service;

import com.verygood.security.coding.api.Encryption;
import com.verygood.security.coding.api.IEncryptionKeyService;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TripleDesEncryptionImpl implements Encryption {

    private static final Charset ENCODING = StandardCharsets.UTF_8;
    private static final String ALGORITHM = "DESede";
    private static final String TRANSFORMATION = ALGORITHM + "/CBC/PKCS5Padding";
    private static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
    private final IEncryptionKeyService secretKeyService;
    private IvParameterSpec ivParameterSpec;
    private SecretKeySpec secretKeySpecs;

    public TripleDesEncryptionImpl(IEncryptionKeyService secretKeyService) {
        this.secretKeyService = secretKeyService;
    }

    @Override
    public String encrypt(String text) {
        if (text == null) {
            return null;
        }
        byte[] bytes = new byte[0];
        try {
            bytes = encryptDecryptInternal(text.getBytes(ENCODING), Cipher.ENCRYPT_MODE);
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException
                | BadPaddingException | IllegalBlockSizeException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public String decrypt(String text) {
        if (text == null) {
            return null;
        }
        byte[] textDecoded = Base64.getDecoder().decode(text);
        byte[] bytes = new byte[0];
        try {
            bytes = encryptDecryptInternal(textDecoded, Cipher.DECRYPT_MODE);
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException
                | BadPaddingException | IllegalBlockSizeException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return new String(bytes, ENCODING);
    }

    private byte[] encryptDecryptInternal(byte[] text, int mode) throws NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {

        SecretKey key = getSecretKey();
        IvParameterSpec iv = getIvParameterSpec();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(mode, key, iv);

        return cipher.doFinal(text);
    }

    private SecretKeySpec getSecretKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (secretKeySpecs == null) {
            secretKeySpecs = new SecretKeySpec(secretKeyService.getKey(), ALGORITHM);
        }
        return secretKeySpecs;
    }

    private IvParameterSpec getIvParameterSpec() throws NoSuchAlgorithmException {
        if (ivParameterSpec == null) {
            SecureRandom randomSecureRandom = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
            byte[] bytes = new byte[8];
            randomSecureRandom.nextBytes(bytes);
            ivParameterSpec = new IvParameterSpec(bytes);
        }
        return ivParameterSpec;
    }

}
