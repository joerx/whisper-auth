package io.yodo.whisper.auth.service;

public interface ClientCredentialService {

    boolean isValidClient(String clientId, String clientSecret);
}
