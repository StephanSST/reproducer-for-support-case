package ch.basler.playground.jwt;

import java.net.URI;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JwtTokenFactory {
  private static final Logger LOG = LoggerFactory.getLogger(JwtTokenFactory.class);

  private static final String AUTH_SERVER_URL = System.getenv("AUTH_SERVER_URL");
  private static final String CLIENT_ID = System.getenv("CLIENT_ID");
  private static final String CLIENT_SECRET = System.getenv("CLIENT_SECRET");
  private static final String USER_ID = System.getenv("USER_ID");
  private static final String PASSWORD = System.getenv("PASSWORD");

  public static String getJwtToken() {
    try {
      OidcClientInfo oidcClientInfo = new OidcClientInfo(URI.create(AUTH_SERVER_URL), CLIENT_ID, CLIENT_SECRET);
      OidcManager oidcManager = new OidcManager(oidcClientInfo, USER_ID, PASSWORD, Instant::now);
      return oidcManager.getCurrentJwtToken();
    } catch (Exception ex) {
      String errorMessage = "Exception trying to create JWT token";
      LOG.error(errorMessage, ex);
      throw new IllegalStateException(errorMessage, ex);
    }
  }

}
