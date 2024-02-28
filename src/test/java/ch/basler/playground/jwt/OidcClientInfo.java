package ch.basler.playground.jwt;

import java.net.URI;

public class OidcClientInfo {

    private final URI realmUri;
    private final String clientId;
    private final String clientSecret;

    public OidcClientInfo(URI realmUri, String clientId, String clientSecret) {
        this.realmUri = realmUri;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public URI getRealmUri() {
        return realmUri;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

}
