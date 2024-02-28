package ch.basler.playground.jwt;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

class HttpJsonHelper {

    String getJsonString(CloseableHttpClient httpclient, HttpPost httpPost) {
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            String jsonString = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return jsonString;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
