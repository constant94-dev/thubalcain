package com.awl.cert.thubalcain.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Slf4j
public class CipherUtils {
    private static Cipher cipher;

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
     * PBEKeySpec 사용해 비밀번호 기반 해싱 후 인코딩 기능
     *
     * @author ethan
     * @params 해싱할 코드 값
     * @return SHA-256 해싱 후 byte[] 인코딩 값 반환
     **/
    public static byte[] hashWithSHA256(String code) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = CipherUtils.generateSalt();
        PBEKeySpec spec = new PBEKeySpec(code.toCharArray(), salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        return factory.generateSecret(spec).getEncoded();
    }

    /**
     * SecureRandom 사용해 보안 처리 된 무작위 수 생성 기능
     *
     * @author ethan
     * @params
     * @return SecureRandom 16바이트 무작위 수 반환
     **/
    public static byte[] generateSalt() {
        SecureRandom secureRandom = new SecureRandom(); // 보안 처리된 무작위 수 생성 객체
        byte[] salt = new byte[16];

        secureRandom.nextBytes(salt);

        return salt;
    }

    protected CipherUtils(){}
}
