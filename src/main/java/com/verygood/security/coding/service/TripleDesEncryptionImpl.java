package com.verygood.security.coding.service;

import com.verygood.security.coding.api.Encryption;
import com.verygood.security.coding.api.ISecretKeyService;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    private final ISecretKeyService secretKeyService;

    public TripleDesEncryptionImpl(ISecretKeyService secretKeyService) {
        this.secretKeyService = secretKeyService;
    }

    @Override
    public String encrypt(String text)
            throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException,
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        byte[] bytes = encryptDecryptInternal(text.getBytes(ENCODING), Cipher.ENCRYPT_MODE);
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public String decrypt(String text) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        byte[] textDecoded = Base64.getDecoder().decode(text);
        byte[] bytes = encryptDecryptInternal(textDecoded, Cipher.DECRYPT_MODE);
        return new String(bytes, ENCODING);
    }

    private byte[] encryptDecryptInternal(byte[] text, int mode) throws NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {

        final SecretKey key = new SecretKeySpec(secretKeyService.getKey(), "DESede");
        // TODO: make random each time
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(mode, key, iv);

        return cipher.doFinal(text);
    }

}
