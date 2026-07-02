package com.example.backend.common.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtil {

    private static String algorithm;
    private static String secret;

    public static void init(String algo, String sec) {
        algorithm = algo;
        secret = sec;

        // ✅ AES key length validation
        if (secret == null ||
            (secret.length() != 16 &&
             secret.length() != 24 &&
             secret.length() != 32)) {

            throw new IllegalArgumentException(
                "Invalid AES key length. Must be 16, 24, or 32 characters."
            );
        }
    }

    public static String encrypt(String value) throws Exception {
        Key key = generateKey(secret);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedByteValue =
                cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(encryptedByteValue);
    }

    public static String decrypt(String value) throws Exception {
        value = value.replaceAll("\\s+", "+");

        Key key = generateKey(secret);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decryptedValue64 = Base64.getUrlDecoder().decode(value);
        byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);

        return new String(decryptedByteValue, StandardCharsets.UTF_8);
    }

    private static Key generateKey(String secretKey) {
        return new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8),
                "AES"
        );
    }
}
