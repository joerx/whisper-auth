package io.yodo.whisper.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class KeyLoaderTest {

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    void testCanLoadDevPrivateKey() throws IOException {
        Resource res = resourceLoader.getResource("classpath:keys/dev_private_key.der");
        res.getInputStream().readAllBytes();
        byte[] keyBytes = res.getInputStream().readAllBytes();
        assertThat("bytes read", keyBytes.length, greaterThan(0));
    }

    @Test
    void testCanLoadDevPublicKey() throws IOException {
        Resource res = resourceLoader.getResource("classpath:keys/dev_public_key.der");
        res.getInputStream().readAllBytes();
        byte[] keyBytes = res.getInputStream().readAllBytes();
        assertThat("bytes read", keyBytes.length, greaterThan(0));
    }
}
