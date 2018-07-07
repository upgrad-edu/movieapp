/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: TokenCryptographyProvider.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.user.provider;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Utility to encrypt/decrypt tokens.
 */
public class TokenCryptographyProvider {

    private static Cipher cipher;
    private static SecretKey secretKey;

    static {
        try {
            final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128); // block size is 128bits

            secretKey = keyGenerator.generateKey();
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException exc) {
            throw new RuntimeException(exc.getMessage(), exc);
        }
    }

    private TokenCryptographyProvider() {
        // prohibit instantiation
    }

    public static String encrypt(String plainText) {

        byte[] plainTextByte = plainText.getBytes();
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedByte = cipher.doFinal(plainTextByte);
            return Base64.getEncoder().encodeToString(encryptedByte);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException exc) {
            throw new RuntimeException(exc.getMessage(), exc);
        }

    }

    public static String decrypt(String encryptedText) {

        byte[] encryptedTextByte = Base64.getDecoder().decode(encryptedText);
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
            return new String(decryptedByte);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException exc) {
            throw new RuntimeException(exc.getMessage(), exc);
        }

    }

}