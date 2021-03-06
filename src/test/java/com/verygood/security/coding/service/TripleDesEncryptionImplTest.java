package com.verygood.security.coding.service;

import static com.verygood.security.coding.service.EncryptionKeyServiceImpl.KEY_SIZE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEmptyString.emptyString;
import static org.mockito.BDDMockito.given;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TripleDesEncryptionImplTest {

    private static final String STRING_TO_ENCRYPT = "test";
    @Mock
    EncryptionKeyServiceImpl secretKeyService;
    @InjectMocks
    private TripleDesEncryptionImpl tripleDesEncryption;

    @Test
    void encrypt_encryptsData() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // given
        given(secretKeyService.getKey()).willReturn(new byte[KEY_SIZE]);

        // when
        String encryptedText = tripleDesEncryption.encrypt(STRING_TO_ENCRYPT);

        // then
        assertThat(encryptedText, is(not(emptyString())));
        assertThat(encryptedText, is(not(STRING_TO_ENCRYPT)));

    }

    @Test
    void encrypt_returnsTheSameValueOnMultipleInvocations() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // given
        given(secretKeyService.getKey()).willReturn(new byte[KEY_SIZE]);

        // when
        String firstEncryption = tripleDesEncryption.encrypt(STRING_TO_ENCRYPT);
        String secondEncryption = tripleDesEncryption.encrypt(STRING_TO_ENCRYPT);

        // then
        assertThat(firstEncryption, is(not(emptyString())));
        assertThat(secondEncryption, is(not(emptyString())));
        assertThat(firstEncryption, is((secondEncryption)));

    }

    @Test
    void decrypt_decryptsData() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // given
        given(secretKeyService.getKey()).willReturn(new byte[KEY_SIZE]);

        // when
        String encryptedText = tripleDesEncryption.encrypt(STRING_TO_ENCRYPT);
        String decryptedText = tripleDesEncryption.decrypt(encryptedText);

        // then
        assertThat(decryptedText, is(not(emptyString())));
        assertThat(decryptedText, is(STRING_TO_ENCRYPT));

    }

    @Test
    void decrypt_ProperlyDecryptsRussian() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // given
        given(secretKeyService.getKey()).willReturn(new byte[KEY_SIZE]);
        String expectedText = "русский";

        // when
        String encryptedText = tripleDesEncryption.encrypt(expectedText);
        String decryptedText = tripleDesEncryption.decrypt(encryptedText);

        // then
        assertThat(decryptedText, is(not(emptyString())));
        assertThat(decryptedText, is(expectedText));

    }

    @Test
    void encrypt_properlyEncryptsEmptyString() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // given
        given(secretKeyService.getKey()).willReturn(new byte[KEY_SIZE]);
        String expectedString = "";

        // when
        String encryptedText = tripleDesEncryption.encrypt(expectedString);
        String decryptedText = tripleDesEncryption.decrypt(encryptedText);

        // then
        assertThat(decryptedText, is(expectedString));

    }

    @Test
    void encrypt_returnsNullOnNullString() {
        // given
        String expectedString = null;

        // when
        String encryptedText = tripleDesEncryption.encrypt(expectedString);
        String decryptedText = tripleDesEncryption.decrypt(encryptedText);

        // then
        assertThat(decryptedText, is(expectedString));

    }
}