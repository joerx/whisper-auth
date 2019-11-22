package io.yodo.whisper.auth.controller;

import io.yodo.whisper.auth.entity.ClientCredentials;
import io.yodo.whisper.auth.entity.TokenResponse;
import io.yodo.whisper.auth.service.ClientCredentialService;
import io.yodo.whisper.commons.security.jwt.JWTTokenHelper;
import io.yodo.whisper.commons.web.error.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final JWTTokenHelper tokenHelper;
    private final ClientCredentialService clientCredentialService;

    public TokenController(JWTTokenHelper tokenHelper, ClientCredentialService clientCredentialService) {
        this.tokenHelper = tokenHelper;
        this.clientCredentialService = clientCredentialService;
    }

    @PostMapping("/token")
    public TokenResponse getToken(@RequestBody ClientCredentials cc) {
        // our custom error handler will map InvalidRequestException to a 400 Bad Request response
        if (!clientCredentialService.isValidClient(cc.getClientId(), cc.getClientSecret())) {
            throw new InvalidRequestException("Invalid client credentials");
        }

        log.debug("client is valid, issuing token");

        String token = tokenHelper.issueToken(cc.getClientId());
        return new TokenResponse(token);
    }
}
