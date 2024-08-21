package com.awl.cert.thubalcain.utils;

import javax.crypto.*;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public class CipherUtils {
    static Cipher cipher;


    /**
     * KeyGenerator 사용해 128비트 길이의 대칭키 생성 기능
     *
     * @author ethan
     * @params
     * @return AES 대칭 키 반환
     **/
    public static SecretKey symmetryGenerateKey() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);

        return generator.generateKey();
    }

    /**
     * KeyPairGenerator 사용해 비대칭 키 생성 기능
     *
     * @author ethan
     * @params
     * @return EC 비대칭 키 반환
     **/
    public static KeyPair asymmetryGenerateKey() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("EC"); // EC 알고리즘 사용
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1"); // 일반적으로 사용되는 타원 곡선 지정

        generator.initialize(ecSpec); // 지정된 타원 곡선 사용해 generator 초기화

        return generator.generateKeyPair(); // 키 쌍 생성 및 반환
    }

    /**
     * Cipher 사용해 인코딩 byte[] 생성 후 Base64 인코딩 기능
     *
     * @author ethan
     * @params 평문 메시지, 대칭 키
     * @return 인코딩 된 메시지 반환
     **/
    public static String messageEncrypt(String message, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Cipher 사용해 디코딩 byte[] 생성 후 평문 메시지 생성 기능
     *
     *
     * @author ethan
     * @params 암호화 된 메시지, 대칭 키
     * @return 디코딩 된 평문 메시지 반환
     **/
    public static String messageDecrypt(String encryptedMsg, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMsg));

        return new String(decryptedBytes);
    }

    /**
     * MessageDigest 사용해 byte[] 생성 후 Base64 인코딩 기능
     *
     *
     * @author ethan
     * @params 해싱할 코드 값
     * @return SHA-512 해싱 후 Base64 인코딩 값 반환
     **/
    public static String hashWithSHA512(String code) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        byte[] bytes = md.digest(code.getBytes());

        return Base64.getEncoder().encodeToString(bytes);
    }

    private CipherUtils(){}
}
