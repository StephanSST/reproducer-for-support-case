package ch.basler.playground.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class OidcTokenRefresher {

    public JSONObject post(String refreshToken, OidcClientInfo oidcClientInfo) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "refresh_token"));
            params.add(new BasicNameValuePair("refresh_token", refreshToken));
            params.add(new BasicNameValuePair("scope", "openid profile"));
            params.add(new BasicNameValuePair("client_id", oidcClientInfo.getClientId()));
            params.add(new BasicNameValuePair("client_secret", oidcClientInfo.getClientSecret()));
            HttpPost httpPost = new HttpPost(oidcClientInfo.getRealmUri());
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            String jsonString = new HttpJsonHelper().getJsonString(httpclient, httpPost);
            return new JSONObject(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
