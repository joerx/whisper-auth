package io.yodo.whisper.auth.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.yodo.whisper.auth.WhisperAuthApplication;
import io.yodo.whisper.auth.entity.TokenResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {WhisperAuthApplication.class})
class TokenApiTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MockMvc mvc;

    private String testClientId = "test";

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${client.credentials.clients.test}")
    private String testClientSecret;

    @Test
    void testCanIssueToken() throws Exception{
        String data = "{\"client_id\": \"" + testClientId + "\", \"client_secret\": \"" + testClientSecret + "\"}";
        mvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void testCannotIssueTokenForInvalidClientSecret() throws Exception{
        String data = "{\"client_id\": \"" + testClientId + "\", \"client_secret\": \"__" + testClientSecret + "__\"}";
        mvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(data))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.token").doesNotExist());
    }

    @Test
    void testCanInspectToken() throws Exception{
        String token = getToken(testClientId, testClientSecret);
        mvc.perform(get("/inspect")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.issuer").value(issuer));
    }

    private String getToken(String clientId, String clientSecret) throws Exception{
        ObjectMapper om = new ObjectMapper();
        String data = "{\"client_id\": \"" + clientId + "\", \"client_secret\": \"" + clientSecret + "\"}";

        MvcResult res = mvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(data))
                .andExpect(status().isOk())
                .andReturn();

        TokenResponse resp = om.readValue(res.getResponse().getContentAsString(), TokenResponse.class);
        return resp.getToken();
    }
}
