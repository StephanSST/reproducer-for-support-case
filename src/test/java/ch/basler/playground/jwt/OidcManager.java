package ch.basler.playground.jwt;

import java.time.Instant;
import java.util.function.Supplier;

import org.apache.hc.core5.http.HttpRequest;
import org.json.JSONObject;

import com.nimbusds.jwt.JWT;

public class OidcManager {
    private final OidcClientInfo oidcClientInfo;
    private final String username;
    private final String password;
    private final OidcTokenRefresher oidcTokenRefresher;
    private final OidcPasswordGrantResolver oidcPasswordGrantResolver;
    private final Supplier<Instant> currentTimeSupplier;
    private JwtManager jwtManager;
    private Instant accessTokenIssuedTime;
    private Instant refreshAccessTokenIssuedTime;
    private JwtRefreshAccessTokenManager jwtRefreshAccessTokenManager;

    public OidcManager(OidcClientInfo oidcClientInfo, String username, String password, Supplier<Instant> currentTimeSupplier) {
        this.oidcClientInfo = oidcClientInfo;
        this.oidcTokenRefresher = new OidcTokenRefresher();
        this.oidcPasswordGrantResolver = new OidcPasswordGrantResolver();
        this.username = username;
        this.password = password;
        this.currentTimeSupplier = currentTimeSupplier;
    }

    public OidcManager(OidcClientInfo oidcClientInfo, String username, String password) {
        this(oidcClientInfo, username, password, Instant::now);
    }

    public JWT getAccessToken() {
        return jwtManager.getAccessToken();
    }

    public int getAccessTokenExpiration() {
        return jwtManager.getExpiration();
    }

    public String getRefreshToken() {
        return jwtRefreshAccessTokenManager.getRefreshToken();
    }

    public int getRefreshAccessTokenExpiration() {
        return jwtRefreshAccessTokenManager.getExpiration();
    }

    public void injectJwtAuthParameters(HttpRequest httpRequest) {
        httpRequest.setHeader("authorization", "Bearer " + getCurrentJwtToken());
    }

    public String getCurrentJwtToken() {
        if (jwtManager == null) {
            obtainNewToken();
        }
        if (isRefreshAccessTokenExpired()) {
            obtainNewToken();
        }
        if (isTokenExpired()) {
            refreshToken();
        }
        return getAccessToken().getParsedString();
    }

    private void refreshToken() {
        this.accessTokenIssuedTime = currentTimeSupplier.get();
        jwtManager = new JwtManager(oidcTokenRefresher.post(getRefreshToken(), oidcClientInfo));
    }

    public boolean isRefreshAccessTokenExpired() {
        return currentTimeSupplier.get().isAfter(refreshAccessTokenIssuedTime.plusSeconds(getRefreshAccessTokenExpiration()));
    }

    public boolean isTokenExpired() {
        return currentTimeSupplier.get().isAfter(accessTokenIssuedTime.plusSeconds(getAccessTokenExpiration()));
    }

    public void obtainNewToken() {
        this.accessTokenIssuedTime = currentTimeSupplier.get();
        this.refreshAccessTokenIssuedTime = currentTimeSupplier.get();
        JSONObject jsonObject = oidcPasswordGrantResolver.post(oidcClientInfo, username, password);
        jwtManager = new JwtManager(jsonObject);
        jwtRefreshAccessTokenManager = new JwtRefreshAccessTokenManager(jsonObject);
    }
}
