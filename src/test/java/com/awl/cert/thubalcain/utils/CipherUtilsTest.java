package com.awl.cert.thubalcain.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class CipherUtilsTest {

    @Test
    @DisplayName("대칭 키 생성 후 사용된 알고리듬 확인")
    void symmetryGenerateKey() throws NoSuchAlgorithmException {
        SecretKey key = CipherUtils.symmetryGenerateKey();
        String alg = key.getAlgorithm();

        assertThat(alg).isEqualTo("AES");
    }

    @Test
    @DisplayName("비대칭 키 생성 후 사용된 알고리듬 확인")
    void asymmetryGenerateKey() throws InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, InvalidKeyException {
        KeyPair pair = CipherUtils.asymmetryGenerateKey();
        String alg = pair.getPrivate().getAlgorithm();

        assertThat(alg).isEqualTo("EC");
    }

    @Test
    @DisplayName("암/복호화 동작 확인")
    void encryptAndDecrypt() throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeyException {
        String originMessage = "암호화 메시지.";
        SecretKey key = CipherUtils.symmetryGenerateKey();

        String encryptedMsg = CipherUtils.messageEncrypt(originMessage, key);
        String decryptedMsg = CipherUtils.messageDecrypt(encryptedMsg, key);

        System.out.println("암호화된 메시지: "+encryptedMsg);
        System.out.println("복호화된 메시지: "+decryptedMsg);

        assertThat(decryptedMsg).isEqualTo(originMessage);
    }

    @DisplayName("서로 다른 비밀번호 일치 여부")
    @ParameterizedTest
    @CsvSource(value = {"123456, 12345", "password1, password2"})
    void hashWithSHA512(String originCode, String otherCode) throws NoSuchAlgorithmException {
        String originHash = CipherUtils.hashWithSHA512(originCode);
        String otherHash = CipherUtils.hashWithSHA512(otherCode);

        assertThat(Objects.equals(originHash, otherHash)).isFalse();
    }
}