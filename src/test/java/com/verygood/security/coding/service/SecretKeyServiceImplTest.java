package com.verygood.security.coding.service;

import static com.verygood.security.coding.service.SecretKeyServiceImpl.KEY_SIZE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.junit.jupiter.api.Test;

class SecretKeyServiceImplTest {

    private SecretKeyServiceImpl secretKeyService;

    @Test
    void getKey_retuns24BytesKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // given
        String keyInString = "test";
        secretKeyService = new SecretKeyServiceImpl(keyInString);

        // when
        byte[] actualEncryptionKey = secretKeyService.getKey();

        // then
        assertThat(actualEncryptionKey.length, is(KEY_SIZE));
        String keyAfterGeneration = new String(actualEncryptionKey, StandardCharsets.UTF_8);
        assertThat(keyInString, is(not(keyAfterGeneration)));
    }
}