package io.yodo.whisper.auth.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@ConfigurationProperties(prefix = "client.credentials")
public class ClientCredentialServiceImpl implements ClientCredentialService {

    private Map<String, String> clients;

    public boolean isValidClient(String clientId, String clientSecret) {
        String secret = clients.get(clientId);
        return secret != null && secret.equals(clientSecret);
    }

    public void setClients(Map<String, String> clients) {
        this.clients = clients;
    }
}
