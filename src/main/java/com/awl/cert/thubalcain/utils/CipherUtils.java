package com.awl.cert.thubalcain.utils;

import javax.crypto.*;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public class CipherUtils {
    static Cipher cipher;

    public static SecretKey symmetryGenerateKey() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);

        return generator.generateKey();
    }
    
    public static KeyPair asymmetryGenerateKey() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("EC"); // EC 알고리즘 사용
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1"); // 일반적으로 사용되는 타원 곡선 지정

        generator.initialize(ecSpec); // 지정된 타원 곡선 사용해 generator 초기화

        return generator.generateKeyPair(); // 키 쌍 생성 및 반환
    }
    
    public static String messageEncrypt(String message, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    
    public static String messageDecrypt(String encryptedMsg, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMsg));

        return new String(decryptedBytes);
    }

    public static String hashWithSHA512(String code) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        byte[] bytes = md.digest(code.getBytes());

        return Base64.getEncoder().encodeToString(bytes);
    }

    private CipherUtils(){}
}
