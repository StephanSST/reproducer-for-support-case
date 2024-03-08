package ch.basler.playground;

import java.io.IOException;
import java.io.PrintWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "MyProtectedServlet", urlPatterns = "/protected", loadOnStartup = 1)
@ServletSecurity(@HttpConstraint(rolesAllowed = {"AuthenticatedRole", "RACFPrefixPSANF"}))
public class MyProtectedServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger LOG = LoggerFactory.getLogger(MyProtectedServlet.class);

  @Override
  protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
    LOG.info("doGet called.");

    httpServletResponse.setContentType("text/html");
    PrintWriter printWriter = httpServletResponse.getWriter();
    printWriter.println("<html><body>");

    printWriter.println("<h1>OIDC Keycloak/RH SSO Test Servlet</h1>");

    printWriter.println("<p>");
    printWriter.println("Aktueller Benutzer: " + httpServletRequest.getUserPrincipal().getName() + "<br>");
    printWriter.println("isUserInRole(AuthenticatedRole): " + httpServletRequest.isUserInRole("AuthenticatedRole") + "<br>");
    printWriter.println("isUserInRole(RACFPrefixPSANF): " + httpServletRequest.isUserInRole("RACFPrefixPSANF") + "<br>");
    printWriter.println("isUserInRole(non-existing-role): " + httpServletRequest.isUserInRole("non-existing-role") + "<br>");
    printWriter.println("</p>");

    printWriter.println("</body></html>");
  }


}
