package com.epam.esm.controller;

import com.epam.esm.exception.AuthenticationException;
import com.epam.esm.model.dto.AuthenticationDto;
import com.epam.esm.service.AccountService;
import lombok.Data;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.authorization.client.util.HttpResponseException;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.*;

@Data
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private static final int USR_ALREADY_EXISTS_ERROR_CODE = 40012;
    private static final int CREDENTIAL_NOT_VALID_ERROR_CODE = 40011;

    private final AccountService accountService;

    @Value("${keycloak.credentials.secret}")
    private String secret;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak-admin.login}")
    private String adminLogin;
    @Value("${keycloak-admin.password}")
    private String adminPassword;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationDto dto) {
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", secret);
        clientCredentials.put("grant_type", "password");
        Configuration configuration = new Configuration(authServerUrl,
                realm, clientId, clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);

        AccessTokenResponse response;
        try {
            response = authzClient.obtainAccessToken(dto.getName(), dto.getPassword());
        } catch (HttpResponseException e) {
            throw new AuthenticationException("credential Not Valid", CREDENTIAL_NOT_VALID_ERROR_CODE);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/signUp")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody AuthenticationDto dto) {
        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(authServerUrl)
                .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("admin-cli")
                .username(adminLogin).password(adminPassword)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
        keycloak.tokenManager().getAccessToken();

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(dto.getName());

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        Response response = usersResource.create(user);
        if (response.getStatus() == HttpStatus.CONFLICT.value()) {
            throw new AuthenticationException("User already exists", USR_ALREADY_EXISTS_ERROR_CODE);
        }

        if (response.getStatus() == HttpStatus.CREATED.value()) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            accountService.saveAccount(userId, dto.getName());

            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(dto.getPassword());

            UserResource userResource = usersResource.get(userId);
            userResource.resetPassword(passwordCred);
            RoleRepresentation realmRoleUser = realmResource.roles().get("app-user").toRepresentation();
            userResource.roles().realmLevel().add(Collections.singletonList(realmRoleUser));
        }
        return ResponseEntity.ok(dto);
    }
}

