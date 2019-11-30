package io.yodo.whisper.auth;

import io.yodo.whisper.commons.security.config.JWTConfigAdapter;
import io.yodo.whisper.commons.security.jwt.TokenHelper;
import io.yodo.whisper.commons.security.jwt.TokenIssuer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("security.jwt")
public class JWTConfig extends JWTConfigAdapter {

    @Bean
    public TokenIssuer tokenIssuer(TokenHelper tokenHelper) {
        return new TokenIssuer(tokenHelper, getIssuer());
    }
}
