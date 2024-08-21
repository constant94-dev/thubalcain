package com.awl.cert.thubalcain.service.impl;

import com.awl.cert.thubalcain.service.JwtsService;
import com.awl.cert.thubalcain.service.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.Password;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static com.awl.cert.thubalcain.utils.CipherUtils.hashWithSHA512;

@Slf4j
@Service
public class JwtsServiceImpl implements JwtsService {
    private KeyAlgorithm<Password, Password> alg = Jwts.KEY.PBES2_HS512_A256KW;
    private AeadAlgorithm enc = Jwts.ENC.A256CBC_HS512;
    
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
    public String createJWE(String code, UserDTO userDTO) {
        return Jwts.builder()
                .header()
                .add(createHeader())
                .and()
                .claims(createClaim())
                .encryptWith(createPassword(code), alg, enc)
                .compact();
    }

    private Password createPassword(String code) {
        return Keys.password(code.toCharArray());
    }

    private Map<String, ?> createClaim() {
        return null;
    }

    private Map<String, ?> createHeader() {
        return null;
    }
}
