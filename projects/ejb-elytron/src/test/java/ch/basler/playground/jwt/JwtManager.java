package ch.basler.playground.jwt;

import java.text.ParseException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

class JwtManager {

    private static final Logger logger = LoggerFactory.getLogger(JwtManager.class);

    private final JWT accessToken;
    private final int expiration;

    JwtManager(JSONObject jsonObject) {
        this.accessToken = getAccessToken(jsonObject);
        this.expiration = getExpiration(jsonObject);
    }

    private static JWT getAccessToken(JSONObject jsonObject) {
        try {
            return JWTParser.parse(jsonObject.get("access_token").toString());
        } catch (ParseException e) {
            logger.error("Unexpected JSON: " + jsonObject.toString());
            throw new RuntimeException(e);
        }
    }

    private static int getExpiration(JSONObject jsonObject) {
        return (Integer) jsonObject.get("expires_in");
    }

    JWT getAccessToken() {
        return accessToken;
    }

    int getExpiration() {
        return expiration;
    }
}
