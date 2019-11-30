package io.yodo.whisper.auth;

import io.yodo.whisper.commons.security.jwt.TokenDecoder;
import io.yodo.whisper.commons.security.jwt.TokenHelper;
import io.yodo.whisper.commons.security.jwt.TokenIssuer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("security.jwt")
public class JWTConfig {

    private String issuer;

    private String secret;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Bean
    public TokenHelper tokenHelper(JWTConfig config) {
        return new TokenHelper(config.getSecret());
    }

    @Bean
    public TokenIssuer tokenIssuer(JWTConfig config, TokenHelper tokenHelper) {
        return new TokenIssuer(tokenHelper, config.getIssuer());
    }

    @Bean
    public TokenDecoder tokenDecoder(JWTConfig config, TokenHelper tokenHelper) {
        return new TokenDecoder(tokenHelper, config.getIssuer());
    }
}
