package com.verygood.security.coding.service;

import static com.verygood.security.coding.service.SecretKeyServiceImpl.KEY_SIZE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEmptyString.emptyString;
import static org.mockito.BDDMockito.given;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TripleDesEncryptionImplTest {

    private static final String STRING_TO_ENCRYPT = "test";
    @Mock
    SecretKeyServiceImpl secretKeyService;
    @InjectMocks
    private TripleDesEncryptionImpl tripleDesEncryption;

    @BeforeEach
    void setUp() throws InvalidKeySpecException, NoSuchAlgorithmException {
        given(secretKeyService.getKey()).willReturn(new byte[KEY_SIZE]);
    }

    @Test
    void encrypt_encryptsData()
            throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException,
            InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            NoSuchPaddingException {
        // when
        String encryptedText = tripleDesEncryption.encrypt(STRING_TO_ENCRYPT);

        // then
        assertThat(encryptedText, is(not(emptyString())));
        assertThat(encryptedText, is(not(STRING_TO_ENCRYPT)));

    }

    @Test
    void encrypt_decryptsData()
            throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException,
            InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            NoSuchPaddingException {
        // when
        String encryptedText = tripleDesEncryption.encrypt(STRING_TO_ENCRYPT);
        String decryptedText = tripleDesEncryption.decrypt(encryptedText);

        // then
        assertThat(decryptedText, is(not(emptyString())));
        assertThat(decryptedText, is(STRING_TO_ENCRYPT));

    }
}