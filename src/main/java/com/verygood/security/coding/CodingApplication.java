package com.verygood.security.coding;

import com.verygood.security.coding.api.Encryption;
import com.verygood.security.coding.api.IEncryptionKeyService;
import com.verygood.security.coding.api.exception.WrongInputArgumentsException;
import com.verygood.security.coding.service.EncryptionKeyServiceImpl;
import com.verygood.security.coding.service.TripleDesEncryptionImpl;
import org.apache.commons.lang3.StringUtils;

public class CodingApplication {

    static final String ENCRYPTION_KEY_ENV_VARIABLE = "ENCRYPTION_KEY";
    static final String TEXT_TO_ENCRYPT_ENV_VARIABLE = "TEXT_TO_ENCRYPT";
    private static String encryptionKey;
    private static String textToEncrypt;

    public static void main(String[] args) {
        setArguments(args);

        IEncryptionKeyService encryptionKeyService = new EncryptionKeyServiceImpl(encryptionKey);
        Encryption encryptionService = new TripleDesEncryptionImpl(encryptionKeyService);
        String encryptedText = encryptionService.encrypt(textToEncrypt);
        String decryptedText = encryptionService.decrypt(encryptedText);

        System.out.println("Encrypted text: " + encryptedText);
        System.out.println("Decrypted text: " + decryptedText);
    }

    private static void setArguments(String[] args) {
        if (args.length <= 1) {
            readArgsFromEnvironmentVariables();
            if (StringUtils.isAllBlank(encryptionKey, textToEncrypt)) {
                throw new WrongInputArgumentsException("Arguments are empty or half empty. Please input an encryption key and a text, "
                        + "e.g. ./application.jar FHDGYR 'text to encrypt'");
            }
        }
        else {
            encryptionKey = args[0];
            textToEncrypt = args[1];
        }
    }

    private static void readArgsFromEnvironmentVariables() {
        String encryptionKeyFromEnv = System.getenv(ENCRYPTION_KEY_ENV_VARIABLE);
        String textToEncryptFromEnv = System.getenv(TEXT_TO_ENCRYPT_ENV_VARIABLE);
        encryptionKey = encryptionKeyFromEnv;
        textToEncrypt = textToEncryptFromEnv;
    }
}
