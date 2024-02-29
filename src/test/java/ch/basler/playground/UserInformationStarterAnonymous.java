package ch.basler.playground;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserInformationStarterAnonymous {

  private static final Logger LOG = LoggerFactory.getLogger(UserInformationStarterAnonymous.class);
  private static final String URL = "http-remoting://localhost:8080";

  public static void main(String[] args) throws Exception {
    final Hashtable<String, String> jndiProperties = new Hashtable<>();
    jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
    jndiProperties.put(Context.PROVIDER_URL, URL);
    jndiProperties.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");
    jndiProperties.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
    jndiProperties.put("jboss.naming.client.ejb.context", "true");
    final Context context = new InitialContext(jndiProperties);

    UserInformation userInformation = (UserInformation) context.lookup(UserInformation.JNDI_NAME);

    LOG.info("Calling public method of EJB:");
    String result = userInformation.executePublicMethod("inputPublic");
    LOG.info(result);
  }

}
