package io.yodo.whisper.auth;

import io.yodo.whisper.commons.security.jwt.TokenDecoder;
import io.yodo.whisper.commons.security.jwt.TokenIssuer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenConfigTest {

    @Autowired
    TokenIssuer tokenIssuer;

    @Autowired
    TokenDecoder tokenDecoder;

    @Value("${security.jwt.issuer}")
    String issuer;

    @Test
    void testCanCreateTokenIssuer() {
        assertNotNull(tokenIssuer);
        assertEquals(issuer, tokenIssuer.getIssuer());
        assertNotNull(tokenIssuer.getAlgo());
    }

    @Test
    void testCanCreateTokenDecoder() {
        assertNotNull(tokenDecoder);
        assertEquals(issuer, tokenDecoder.getIssuer());
        assertNotNull(tokenDecoder.getAlgo());
    }
}
