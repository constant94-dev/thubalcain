package com.awl.cert.thubalcain.service.impl;

import com.awl.cert.thubalcain.service.JwtsService;
import com.awl.cert.thubalcain.service.dto.RequestTokenDTO;
import com.awl.cert.thubalcain.utils.DateTimeUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.Password;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import static com.awl.cert.thubalcain.utils.CipherUtils.hashWithSHA512;

@Slf4j
@Service
public class JwtsServiceImpl implements JwtsService {
    private final KeyAlgorithm<Password, Password> ALG = Jwts.KEY.PBES2_HS512_A256KW;
    private final AeadAlgorithm ENC = Jwts.ENC.A256CBC_HS512;
    @Value("${token.issuer}")
    private String iss; // 발급자의 URL or 이름

    /**
     * 토큰 요청 전 인가코드 발급
     * 
     * @author ethan
     * @params password
     * @return cipher string
     **/
    @Override
    public String createAuthorizeCode(String password) {
        final String code;
        try {
            code = hashWithSHA512(password);
        } catch (NoSuchAlgorithmException e) {
            return "Hashing 알고리듬을 찾을 수 없습니다.";
        }

        return code;
    }

    /**
     * 인가코드를 바탕으로 토큰 생성
     *
     * @author ethan
     * @params athorizae code
     * @return Encrypted JWT
     **/
    @Override
    public String createJWE(String code, RequestTokenDTO requestTokenDTO) {
        return Jwts.builder()
                .header()
                    .add(createHeader())
                    .and()
                .claims(createClaim(requestTokenDTO))
                .encryptWith(createPassword(code), ALG, ENC)
                .compact();
    }

    private Password createPassword(String code) {
        return Keys.password(code.toCharArray());
    }

    private Map<String, ?> createClaim(RequestTokenDTO requestTokenDTO) {
        LocalDateTime issuedAt = DateTimeUtils.generateIssuedAt();
        LocalDateTime expiredAt = DateTimeUtils.generateExpiredAt(issuedAt);
        Date issuedDate = DateTimeUtils.convertLocalDateTimeToDate(issuedAt);
        Date expireDate = DateTimeUtils.convertLocalDateTimeToDate(expiredAt);
        Date notBefore = DateTimeUtils.generateNotBeforeDate();

        return Jwts.claims()
                .issuer(iss) // 발급자
                .subject(requestTokenDTO.getName()) // 제목
                .audience().add(requestTokenDTO.getAud()).and() // 대상(공개 범위)
                .expiration(expireDate) // 만료 날짜
                .notBefore(notBefore) // JWT가 유효해지는 시점 (이 날짜 이전에는 JWT가 유효하지 않음)
                .issuedAt(issuedDate) // 발급 날짜
                .build();
    }

    private Map<String, ?> createHeader() {
        return Jwts.header()
                .contentType("application/json")
                .build();
    }
}
