package com.awl.cert.thubalcain.controller.api;

import com.awl.cert.thubalcain.controller.api.dto.RequestAuthorizeDTO;
import com.awl.cert.thubalcain.service.JwtsService;
import com.awl.cert.thubalcain.service.dto.RequestTokenDTO;
import com.awl.cert.thubalcain.utils.ConverterJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtsService jwtsService;

    /* NOTE
     *  mockMvc 파라미터 description
     *  perform(): controller layer 테스트 하기 위해 사용. 실제 서버를 구동하지 않고도 controller의 http 요청과 응답을 시뮬레이션 함.
     *  contentType(MediaType.APPLICATION_JSON): 요청의 Content-Type 헤더 설정. (요청의 본문이 JSON 형식임을 나타냄)
     *  content(ConverterJsonUtils.writeToJsonString(authorizeDTO)): 요청의 본문을 설정. (객체를 JSON 문자열로 변환하여 요청 본문에 포함)
     *  andExpect(status().isOk()): HTTP 응답의 상태 코드가 200 OK인지 확인.
     *  andExpect(content().string(not(emptyOrNullString()))): 응답 본문이 null이 아니고 빈 문자열이 아닌지를 확인.
     * */
    @DisplayName("mvc controller 레이어 검증, 인가 코드 발급 http 요청")
    @Test
    void createAuthorizeCode() throws Exception {
        RequestAuthorizeDTO authorizeDTO = ConverterJsonUtils.readFileToMapper("request/create-authorize-code-request.json", RequestAuthorizeDTO.class);

        when(jwtsService.createAuthorizeCode(authorizeDTO)).thenReturn("authorize_code");

        mockMvc.perform(post("/api/auth/create/code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ConverterJsonUtils.writeToJsonString(authorizeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(not(emptyOrNullString())));
    }

    @DisplayName("mvc controller 레이어 검증, 인증 토큰 발급 http 요청")
    @Test
    void createJWE() throws Exception {
        RequestTokenDTO.Request tokenDTO = ConverterJsonUtils.readFileToMapper("request/create-jwt-encrypted-request.json", RequestTokenDTO.Request.class);

        when(jwtsService.createJWE(any(RequestTokenDTO.Request.class))).thenReturn("jwe_token");

        MvcResult result = mockMvc.perform(post("/api/auth/create/jwe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ConverterJsonUtils.writeToJsonString(tokenDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .contains("jwe_token");
    }
}