package com.verygood.security.coding.api;

public interface Encryption {
  String encrypt(String text);
  String decrypt(String encryptedData);
}
