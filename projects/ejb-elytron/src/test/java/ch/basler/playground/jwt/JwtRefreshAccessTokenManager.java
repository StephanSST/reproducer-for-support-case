package ch.basler.playground.jwt;

import org.json.JSONObject;

class JwtRefreshAccessTokenManager {
    private final String refreshToken;
    private final int expiration;

    JwtRefreshAccessTokenManager(JSONObject jsonObject) {
        this.refreshToken = getRefreshToken(jsonObject);
        this.expiration = getExpiration(jsonObject);
    }

    private static String getRefreshToken(JSONObject jsonObject) {
        return jsonObject.get("refresh_token").toString();
    }

    private static int getExpiration(JSONObject jsonObject) {
        return (Integer) jsonObject.get("refresh_expires_in");
    }

    String getRefreshToken() {
        return refreshToken;
    }

    int getExpiration() {
        return expiration;
    }
}
