package com.verygood.security.coding.api;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface ISecretKeyService {
    byte[] getKey() throws NoSuchAlgorithmException, InvalidKeySpecException;
}
