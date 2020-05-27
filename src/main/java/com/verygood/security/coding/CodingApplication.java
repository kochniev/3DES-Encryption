package com.verygood.security.coding;

import com.verygood.security.coding.api.Encryption;
import com.verygood.security.coding.api.IEncryptionKeyService;
import com.verygood.security.coding.api.exception.WrongInputArgumentsException;
import com.verygood.security.coding.service.EncryptionKeyServiceImpl;
import com.verygood.security.coding.service.TripleDesEncryptionImpl;

public class CodingApplication {

    public static void main(String[] args) {
        if (args.length <= 1) {
            throw new WrongInputArgumentsException("Arguments are empty or half empty. Please input an encryption key and a text, "
                    + "e.g. ./application.jar FHDGYR 'text to encrypt'");
        }

        String encryptionKey = args[0];
        String textToEncrypt = args[1];
        IEncryptionKeyService encryptionKeyService = new EncryptionKeyServiceImpl(encryptionKey);
        Encryption encryptionService = new TripleDesEncryptionImpl(encryptionKeyService);
        String encryptedText = encryptionService.encrypt(textToEncrypt);
        String decryptedText = encryptionService.decrypt(encryptedText);

        System.out.println("Encrypted text: " + encryptedText);
        System.out.println("Decrypted text: " + decryptedText);
        System.exit(0);
    }
}
