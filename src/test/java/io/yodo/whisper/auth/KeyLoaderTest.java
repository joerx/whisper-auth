package io.yodo.whisper.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration.properties")
public class KeyLoaderTest {

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    void testCanLoadDevKeys() throws IOException {
        Resource res = resourceLoader.getResource("classpath:keys/whisper-auth-dev-pub.der");
        res.getInputStream().readAllBytes();
        byte[] keyBytes = res.getInputStream().readAllBytes();
        assertThat("bytes read", keyBytes.length, greaterThan(0));
    }

    @Test
    void testCanLoadTestKeys() throws IOException {
        Resource res = resourceLoader.getResource("classpath:keys/whisper-auth-test-pub.der");
        res.getInputStream().readAllBytes();
        byte[] keyBytes = res.getInputStream().readAllBytes();
        assertThat("bytes read", keyBytes.length, greaterThan(0));
    }
}
