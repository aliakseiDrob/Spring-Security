package com.epam.esm.controller;

import com.epam.esm.exception.AuthenticationException;
import com.epam.esm.model.dto.AuthenticationDto;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Value("${keycloak.credentials.secret}")
    private String secret;
    @Value("${keycloak.resource}")
    private String resource;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationDto authenticationDto) {
        HttpResponse response = sendRequestToAuthServer(authenticationDto);
        return creteLoginResponse(response);
    }

    private HttpResponse sendRequestToAuthServer(AuthenticationDto authenticationDto) {
        try {
            return Request.Post("http://localhost:8080/auth/realms/Demo-Realm/protocol/openid-connect/token").bodyForm(
                    Form.form().add("grant_type", "password")
                            .add("client_id", resource)
                            .add("client_secret", secret)
                            .add("username", authenticationDto.getName())
                            .add("password", authenticationDto.getPassword()).build())
                    .execute().returnResponse();
        } catch (IOException exception) {
            throw new AuthenticationException("request can not be executed");
        }
    }

    private ResponseEntity<Object> creteLoginResponse(HttpResponse response) {
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new AuthenticationException("credential Not Valid");
        }
        try {
            JSONObject jsonObj = new JSONObject(EntityUtils.toString(response.getEntity()));
            return ResponseEntity.ok(Collections.singletonMap("access_token", jsonObj.get("access_token")));
        } catch (IOException | JSONException exception) {
            throw new AuthenticationException("Incorrect response");
        }
    }
}

