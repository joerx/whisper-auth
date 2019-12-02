package io.yodo.whisper.auth.controller;

import io.yodo.whisper.auth.entity.ClientCredentials;
import io.yodo.whisper.auth.entity.TokenResponse;
import io.yodo.whisper.auth.service.ClientCredentialService;
import io.yodo.whisper.commons.security.jwt.TokenAuthentication;
import io.yodo.whisper.commons.security.jwt.TokenDetails;
import io.yodo.whisper.commons.security.jwt.TokenIssuer;
import io.yodo.whisper.commons.security.jwt.UnauthorizedException;
import io.yodo.whisper.commons.web.error.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final TokenIssuer tokenIssuer;

    private final ClientCredentialService clientCredentialService;

    public TokenController(TokenIssuer tokenIssuer, ClientCredentialService clientCredentialService) {
        this.tokenIssuer = tokenIssuer;
        this.clientCredentialService = clientCredentialService;
    }

    @PostMapping("/token")
    public TokenResponse createToken(@RequestBody ClientCredentials cc) {
        // our custom error handler will map InvalidRequestException to a 400 Bad Request response
        if (!clientCredentialService.isValidClient(cc.getClientId(), cc.getClientSecret())) {
            throw new InvalidRequestException("Invalid client credentials");
        }

        log.debug("client is valid, issuing token");

        String token = tokenIssuer.issueToken(cc.getClientId());
        return new TokenResponse(token);
    }

    @GetMapping("/inspect")
    public TokenDetails inspectToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof TokenAuthentication)) {
            throw new UnauthorizedException("Invalid authentication");
        }
        return ((TokenAuthentication) auth).getTokenDetails();
    }
}
