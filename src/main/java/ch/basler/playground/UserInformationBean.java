package ch.basler.playground;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.annotation.Resource;
import jakarta.annotation.Resources;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;

@Resources({//
    @Resource(name = "java:jboss/exported/" + UserInformation.JNDI_NAME, lookup = "java:module/UserInformationBean"),
    @Resource(name = "java:/" + UserInformation.JNDI_NAME, lookup = "java:module/UserInformationBean")})
@Stateless(mappedName = UserInformation.JNDI_NAME)
@Remote(UserInformation.class)
@SecurityDomain("my-basler-domain")
public class UserInformationBean implements UserInformation {
  private static final String USER_WITH_PERMISSIONS = "User %s with RACFPrefixPSANF: %s and AuthenticatedRole: %s.";

  private static final Logger LOG = LoggerFactory.getLogger(UserInformationBean.class);

  @Resource
  private SessionContext sessionContext;

  @Override
  @RolesAllowed({"AuthenticatedRole", "RACFPrefixPSANF"})
  public String executeProtectedMethod(String inputValue) {
    LOG.info("executeProtectedMethod called with parameter: " + inputValue);
    String message = String.format(USER_WITH_PERMISSIONS, //
        sessionContext.getCallerPrincipal().getName(), //
        sessionContext.isCallerInRole("RACFPrefixPSANF"), //
        sessionContext.isCallerInRole("AuthenticatedRole"));
    LOG.info(message);
    return message;
  }

  @Override
  @PermitAll
  public String executePublicMethod(String inputValue) {
    LOG.info("executePublicMethod called with parameter: " + inputValue);
    String message = String.format(USER_WITH_PERMISSIONS, //
        sessionContext.getCallerPrincipal().getName(), //
        sessionContext.isCallerInRole("RACFPrefixPSANF"), //
        sessionContext.isCallerInRole("AuthenticatedRole"));
    LOG.info(message);
    return message;
  }

}
