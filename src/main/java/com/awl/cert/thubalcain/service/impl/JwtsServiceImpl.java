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
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.awl.cert.thubalcain.utils.CipherUtils.hashWithSHA512;

@Slf4j
@Service
public class JwtsServiceImpl implements JwtsService {
    private final KeyAlgorithm<Password, Password> ALG = Jwts.KEY.PBES2_HS512_A256KW;
    private final AeadAlgorithm ENC = Jwts.ENC.A256CBC_HS512;
    
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
        Map<String, String> claimByToken = new HashMap<>();

        LocalDateTime issuedAt = DateTimeUtils.generateIssuedAt();
        LocalDateTime expiredAt = DateTimeUtils.generateExpiration(issuedAt);

        claimByToken.put("iss", requestTokenDTO.getEmail()); // 발급자
        claimByToken.put("sub", requestTokenDTO.getName()); // 제목
        claimByToken.put("aud", requestTokenDTO.getAud()); // 대상
        claimByToken.put("iat", issuedAt.toString()); // 발급 시점
        claimByToken.put("exp", expiredAt.toString()); // 만료 시점

        return claimByToken;
    }

    private Map<String, ?> createHeader() {
        return null;
    }
}
