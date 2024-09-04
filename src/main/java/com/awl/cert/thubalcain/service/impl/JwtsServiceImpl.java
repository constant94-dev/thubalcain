package com.awl.cert.thubalcain.service.impl;

import com.awl.cert.thubalcain.common.ErrorCode;
import com.awl.cert.thubalcain.controller.dto.ViewCreateToken;
import com.awl.cert.thubalcain.controller.vo.request.CreateAuthorizeRequest;
import com.awl.cert.thubalcain.service.JwtsService;
import com.awl.cert.thubalcain.utils.DateTimeUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.Password;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import static com.awl.cert.thubalcain.utils.CipherUtils.hashWithSHA256;

@Slf4j
@Service
public class JwtsServiceImpl implements JwtsService {
    private final KeyAlgorithm<Password, Password> ALG = Jwts.KEY.PBES2_HS512_A256KW;
    private final AeadAlgorithm ENC = Jwts.ENC.A256CBC_HS512;
    @Value("${token.issuer}")
    private String iss; // 발급자의 URL or 이름
    @Value("${token.audience}")
    private String aud; // 대상

    /**
     * 토큰 요청 전 인가코드 발급
     *
     * @author ethan
     * @params authorize dto (password)
     * @return Authorize Code
     **/
    @Override
    public String createAuthorizeCode(CreateAuthorizeRequest createAuthorizeRequest) {
        try {
            final byte[] code = hashWithSHA256(createAuthorizeRequest.password());
            return Base64.encodeBase64String(code);
        } catch (NoSuchAlgorithmException e) {
            return ErrorCode.NO_SUCH_ALGORITHM.getReason();
        } catch (InvalidKeySpecException e) {
            return ErrorCode.INVALID_KEY_SPEC.getReason();
        }
    }

    /**
     * 인가코드를 바탕으로 토큰 생성
     *
     * @author ethan
     * @params token request dto (email, name, aud, authCode)
     * @return Encrypted JWT
     **/
    @Override
    public String createJWE(ViewCreateToken.Request requestTokenDTO) {
        return Jwts.builder()
                .header()
                    .add(createHeader())
                    .and()
                .claims(createClaim(requestTokenDTO))
                .encryptWith(createPassword(requestTokenDTO.getAuthCode()), ALG, ENC)
                .compact();
    }

    private Password createPassword(String code) {
        return Keys.password(code.toCharArray());
    }

    private Map<String, ?> createClaim(ViewCreateToken.Request requestTokenDTO) {
        LocalDateTime issuedAt = DateTimeUtils.generateIssuedAt();
        LocalDateTime expiredAt = DateTimeUtils.generateExpiredAt(issuedAt);
        Date issuedDate = DateTimeUtils.convertLocalDateTimeToDate(issuedAt);
        Date expireDate = DateTimeUtils.convertLocalDateTimeToDate(expiredAt);
        Date notBefore = DateTimeUtils.generateNotBeforeDate();

        return Jwts.claims()
                .issuer(iss) // 발급자
                .audience().add(requestTokenDTO.getAud()).and() // 대상
                .subject(requestTokenDTO.getName()) // 제목
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
