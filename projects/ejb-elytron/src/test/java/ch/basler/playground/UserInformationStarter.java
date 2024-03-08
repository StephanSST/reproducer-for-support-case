package ch.basler.playground;

import java.util.Hashtable;
import java.util.concurrent.Callable;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.wildfly.security.auth.client.MatchRule;
import org.wildfly.security.sasl.SaslMechanismSelector;
import ch.basler.playground.jwt.JwtTokenFactory;

public class UserInformationStarter {

  private static final Logger LOG = LoggerFactory.getLogger(UserInformationStarter.class);
  private static final String URL = "http-remoting://localhost:8080";

  static final Callable<String> callable = () -> {
    final Hashtable<String, String> jndiProperties = new Hashtable<>();
    jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
    jndiProperties.put(Context.PROVIDER_URL, URL);
    final Context context = new InitialContext(jndiProperties);

    UserInformation userInformation = (UserInformation) context.lookup(UserInformation.JNDI_NAME);

    LOG.info("Calling public method of EJB:");
    String result = userInformation.executeProtectedMethod("inputProtected");
    LOG.info(result);
    return result;
  };

  public static void main(String[] args) throws Exception {
    LOG.info("*** EJB Client API ***");

    AuthenticationConfiguration common = AuthenticationConfiguration.empty().setSaslMechanismSelector(SaslMechanismSelector.NONE.addMechanism("OAUTHBEARER"));
    AuthenticationConfiguration quickstartUser = common.useBearerTokenCredential(new org.wildfly.security.credential.BearerTokenCredential(getAccessToken()));
    final AuthenticationContext authCtx1 = AuthenticationContext.empty().with(MatchRule.ALL, quickstartUser);

    LOG.info(authCtx1.runCallable(callable));

    LOG.info("*** EJB Client API ***");
  }

  private static String getAccessToken() {
    String jwtToken = JwtTokenFactory.getJwtToken();
    if (jwtToken == null) {
      throw new IllegalStateException("could not get JWT token from Keycloak");
    }
    LOG.info("adding this jwt token to ejb request: " + jwtToken);
    return jwtToken;
  }

}
