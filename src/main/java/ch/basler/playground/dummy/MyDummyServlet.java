package ch.basler.playground.dummy;

import java.io.IOException;
import java.io.PrintWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "MyDummyServlet", urlPatterns = "/service", loadOnStartup = 1)
@ServletSecurity(@HttpConstraint(rolesAllowed = {"AuthenticatedRole", "RACFPrefixPSANF"}))
public class MyDummyServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private static final Logger LOG = LoggerFactory.getLogger(MyDummyServlet.class);

  @Override
  protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
    httpServletResponse.setContentType("text/html");
    PrintWriter printWriter = httpServletResponse.getWriter();
    printWriter.println("<html><body>");
    printWriter.println("<h1>MyDummyServlet</h1>");

    printWriter.println("<p>");
    printWriter.println("MyDummyServlet called.");
    printWriter.println("</p>");

    printWriter.println("</body></html>");
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    LOG.info("Servlet found, init called");
    super.init(config);
  }

}
