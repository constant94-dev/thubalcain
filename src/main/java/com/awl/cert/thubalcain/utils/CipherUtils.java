package com.awl.cert.thubalcain.utils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CipherUtils {

    /* TODO padding 방식 암호화 고려... */
    public static String createCipherText(String originalMessage) throws NoSuchPaddingException, NoSuchAlgorithmException {
        final Cipher cipher = Cipher.getInstance("PKCS5Padding");
        return "";
    }

    public static String hashWithSHA512(String code) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("SHA-512");

        final byte[] bytes = md.digest(code.getBytes());

        return Base64.getEncoder().encodeToString(bytes);
    }
}
