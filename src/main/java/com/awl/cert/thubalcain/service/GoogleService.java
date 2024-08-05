package com.awl.cert.thubalcain.service;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
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
    @Value("${google-scope}")
    private String SCOPE;


    public RedirectView goGoogleOAuth() {
        String uri = AUTHORIZE_URI+"?redirect_uri="+REDIRECT_URI+"&response_type=code&scope="+SCOPE+"&client_id="+CLIENT_ID+
                "&access_type=offline";

        return new RedirectView(uri);
    }

    public RedirectView loginCallback() {
        try {
            AuthorizationCodeFlow codeFlow = initializeFlow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new RedirectView("html/index.html");
    }

    private AuthorizationCodeFlow initializeFlow() throws IOException {
        AuthorizationCodeFlow test = new AuthorizationCodeFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                new GenericUrl(TOKEN_URI),
                new ClientParametersAuthentication(CLIENT_ID, CLIENT_PW),
                CLIENT_ID,
                AUTHORIZE_URI
        )
                .setScopes(Arrays.asList("email","profile"))
                .setDataStoreFactory(new FileDataStoreFactory(new File("tokens")))
                .build();
        return null;
    }


}
