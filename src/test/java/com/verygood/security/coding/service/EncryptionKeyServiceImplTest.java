package com.verygood.security.coding.service;

import static com.verygood.security.coding.service.EncryptionKeyServiceImpl.KEY_SIZE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.junit.jupiter.api.Test;

class EncryptionKeyServiceImplTest {

    private EncryptionKeyServiceImpl encryptionKeyService;

    @Test
    void getKey_returns24BytesKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // given
        String keyInString = "test";
        encryptionKeyService = new EncryptionKeyServiceImpl(keyInString);

        // when
        byte[] actualEncryptionKey = encryptionKeyService.getKey();

        // then
        assertThat(actualEncryptionKey.length, is(KEY_SIZE));
        String keyAfterGeneration = new String(actualEncryptionKey, StandardCharsets.UTF_8);
        assertThat(keyInString, is(not(keyAfterGeneration)));
    }

    @Test
    void getKey_returns24BytesKeyForNull() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // given
        String keyInString = null;
        encryptionKeyService = new EncryptionKeyServiceImpl(keyInString);

        // when
        byte[] actualEncryptionKey = encryptionKeyService.getKey();

        // then
        assertThat(actualEncryptionKey.length, is(KEY_SIZE));
        String keyAfterGeneration = new String(actualEncryptionKey, StandardCharsets.UTF_8);
        assertThat(keyInString, is(not(keyAfterGeneration)));
    }

    @Test
    void getKey_returns24BytesKeyForEmptyString() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // given
        String keyInString = "";
        encryptionKeyService = new EncryptionKeyServiceImpl(keyInString);

        // when
        byte[] actualEncryptionKey = encryptionKeyService.getKey();

        // then
        assertThat(actualEncryptionKey.length, is(KEY_SIZE));
        String keyAfterGeneration = new String(actualEncryptionKey, StandardCharsets.UTF_8);
        assertThat(keyInString, is(not(keyAfterGeneration)));
    }
}