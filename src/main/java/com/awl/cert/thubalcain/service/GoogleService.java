package com.awl.cert.thubalcain.service;

import com.awl.cert.thubalcain.utils.Base64UrlDecoderUtils;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Arrays;

@Service
public class GoogleService {
    @Value("${google-client-id}")
    private String CLIENT_ID;
    @Value("${google-client-pw}")
    private String CLIENT_PW;
    @Value("${google-authorize-uri}")
    private String AUTHORIZE_URI;
    @Value("${google-token-uri}")
    private String TOKEN_URI;
    @Value("${google-redirect-uri}")
    private String REDIRECT_URI;

    private AuthorizationCodeFlow codeFlow;

    public RedirectView goGoogleOAuth() {
        try {
            codeFlow = initializeFlow();

            String authorizaionUrl = codeFlow.newAuthorizationUrl()
                    .setRedirectUri(REDIRECT_URI)
                    .build();

            return new RedirectView(authorizaionUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RedirectView loginCallback(String code) {
        System.out.println("redirect after code: "+code);
        try {
            TokenResponse response = codeFlow.newTokenRequest(code)
                    .setRedirectUri(REDIRECT_URI)
                    .setGrantType("authorization_code")
                    .execute();

            System.out.println("response body: " + response);

            String userId = response.get("id_token").toString();
            String[] jwtParts = userId.split("\\.");
            if (jwtParts.length == 3) {
                // 헤더 디코딩
                String header = Base64UrlDecoderUtils.decodeBase64Url(jwtParts[0]);
                System.out.println("Header: " + header);

                // 페이로드 디코딩
                String payload = Base64UrlDecoderUtils.decodeBase64Url(jwtParts[1]);
                System.out.println("Payload: " + payload);
            }
        } catch (TokenResponseException e) {
            System.err.println("Token exchange failed: " + e.getDetails());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("html/index.html");
    }

    private AuthorizationCodeFlow initializeFlow() throws IOException {
        return new AuthorizationCodeFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                new GenericUrl(TOKEN_URI),
                new ClientParametersAuthentication(CLIENT_ID, CLIENT_PW),
                CLIENT_ID,
                AUTHORIZE_URI
        )
                .setScopes(Arrays.asList("email","profile"))
                .build();
    }

}
