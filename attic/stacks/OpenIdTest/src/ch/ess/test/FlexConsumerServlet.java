/**
 * Created on 2007-4-14 上午12:54:50
 */
package ch.ess.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageExtension;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.message.sreg.SRegRequest;
import org.openid4java.message.sreg.SRegResponse;

public class FlexConsumerServlet extends javax.servlet.http.HttpServlet {

  private ConsumerManager manager;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    try {
      this.manager = new ConsumerManager();
      manager.setAssociations(new InMemoryConsumerAssociationStore());
      manager.setNonceVerifier(new InMemoryNonceVerifier(5000));
    } catch (ConsumerException e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if ("true".equals(req.getParameter("is_return"))) {
      processReturn(req, resp);
    } else {
      String identifier = (String)req.getSession().getAttribute("openidLogin");
      req.getSession().removeAttribute("openidLogin");
      this.authRequest(identifier, req, resp);
    }
  }

  private void processReturn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Identifier identifier = this.verifyResponse(req);
    Map<String, Object> result = new HashMap<String, Object>();

    if (identifier == null) {
      result.put("status", "LOGIN_FAILED");
    } else {
      result.put("status", "LOGIN_SUCCESSFULL");
      result.put("identifier", identifier.getIdentifier());
            
      result.put("nickname", req.getAttribute("nickname"));
      result.put("email", req.getAttribute("email"));
      result.put("fullname", req.getAttribute("fullname"));
      result.put("dob", req.getAttribute("dob"));
      result.put("gender", req.getAttribute("gender"));
      result.put("postcode", req.getAttribute("postcode"));
      result.put("country", req.getAttribute("country"));
      result.put("language", req.getAttribute("language"));
      result.put("timezone", req.getAttribute("timezone"));

    }

    req.getSession().setAttribute("openid.result", result);

    resp.sendRedirect("bin/OpenIdTest.html");

  }

  // --- placing the authentication request ---
  @SuppressWarnings("unchecked")
  public String authRequest(String userSuppliedString, HttpServletRequest httpReq, HttpServletResponse httpResp)
      throws IOException, ServletException {
    try {
      // configure the return_to URL where your application will receive
      // the authentication responses from the OpenID provider
      // String returnToUrl = "http://example.com/openid";
      String returnToUrl = httpReq.getRequestURL().toString() + "?is_return=true";

      // perform discovery on the user-supplied identifier
      List discoveries = manager.discover(userSuppliedString);

      // attempt to associate with the OpenID provider
      // and retrieve one service endpoint for authentication
      DiscoveryInformation discovered = manager.associate(discoveries);

      // store the discovery information in the user's session
      httpReq.getSession().setAttribute("openid-disc", discovered);

      // obtain a AuthRequest message to be sent to the OpenID provider
      AuthRequest authReq = manager.authenticate(discovered, returnToUrl);

      // Attribute Exchange example: fetching the 'email' attribute
      FetchRequest fetch = FetchRequest.createFetchRequest();
      SRegRequest sregReq = SRegRequest.createFetchRequest();

      
      HttpSession session = httpReq.getSession();
      Boolean nicknameB = (Boolean)session.getAttribute("attr_nickname");
      Boolean emailB = (Boolean)session.getAttribute("attr_email");
      Boolean dateOfBirthB = (Boolean)session.getAttribute("attr_dateOfBirth");
      Boolean genderB = (Boolean)session.getAttribute("attr_gender");
      Boolean postcodeB = (Boolean)session.getAttribute("attr_postcode");
      Boolean countryB = (Boolean)session.getAttribute("attr_country");
      Boolean languageB = (Boolean)session.getAttribute("attr_language");
      Boolean timezoneB = (Boolean)session.getAttribute("attr_timezone");
      Boolean fullnameB = (Boolean)session.getAttribute("attr_fullname");
      
      session.removeAttribute("attr_nickname");
      session.removeAttribute("attr_email");
      session.removeAttribute("attr_dateOfBirth");
      session.removeAttribute("attr_gender");
      session.removeAttribute("attr_postcode");
      session.removeAttribute("attr_country");
      session.removeAttribute("attr_language");
      session.removeAttribute("attr_timezone");
      session.removeAttribute("attr_fullname");
      
      
      if (nicknameB != null && nicknameB) {
        // fetch.addAttribute("nickname",
        // "http://schema.openid.net/contact/nickname", false);
        sregReq.addAttribute("nickname", false);
      }
      if (emailB != null && emailB) {
        fetch.addAttribute("email", "http://schema.openid.net/contact/email", false);
        sregReq.addAttribute("email", false);
      }
      if (fullnameB != null && fullnameB) {
        fetch.addAttribute("fullname", "http://schema.openid.net/contact/fullname", false);
        sregReq.addAttribute("fullname", false);
      }
      if (dateOfBirthB != null && dateOfBirthB) {
        fetch.addAttribute("dob", "http://schema.openid.net/contact/dob", true);
        sregReq.addAttribute("dob", false);
      }
      if (genderB != null && genderB) {
        fetch.addAttribute("gender", "http://schema.openid.net/contact/gender", false);
        sregReq.addAttribute("gender", false);
      }
      if (postcodeB != null && postcodeB) {
        fetch.addAttribute("postcode", "http://schema.openid.net/contact/postcode", false);
        sregReq.addAttribute("postcode", false);
      }
      if (countryB != null && countryB) {
        fetch.addAttribute("country", "http://schema.openid.net/contact/country", false);
        sregReq.addAttribute("country", false);
      }
      if (languageB != null && languageB) {
        fetch.addAttribute("language", "http://schema.openid.net/contact/language", false);
        sregReq.addAttribute("language", false);
      }
      if (timezoneB != null && timezoneB) {
        fetch.addAttribute("timezone", "http://schema.openid.net/contact/timezone", false);
        sregReq.addAttribute("timezone", false);
      }

      // attach the extension to the authentication request
      if (!sregReq.getAttributes().isEmpty()) {
        authReq.addExtension(sregReq);
      }

      if (!discovered.isVersion2()) {
        // Option 1: GET HTTP-redirect to the OpenID Provider endpoint
        // The only method supported in OpenID 1.x
        // redirect-URL usually limited ~2048 bytes
        httpResp.sendRedirect(authReq.getDestinationUrl(true));
        return null;
      }

      // Option 2: HTML FORM Redirection (Allows payloads >2048 bytes)

      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/formredirection.jsp");
      httpReq.setAttribute("prameterMap", httpReq.getParameterMap());
      httpReq.setAttribute("message", authReq);
      // httpReq.setAttribute("destinationUrl", httpResp
      // .getDestinationUrl(false));
      dispatcher.forward(httpReq, httpResp);

    } catch (OpenIDException e) {
      // present error to the user
    }

    return null;
  }

  // --- processing the authentication response ---
  @SuppressWarnings("unchecked")
  public Identifier verifyResponse(HttpServletRequest httpReq) {
    try {
      // extract the parameters from the authentication response
      // (which comes in as a HTTP request from the OpenID provider)
      ParameterList response = new ParameterList(httpReq.getParameterMap());

      // retrieve the previously stored discovery information
      DiscoveryInformation discovered = (DiscoveryInformation)httpReq.getSession().getAttribute("openid-disc");

      // extract the receiving URL from the HTTP request
      StringBuffer receivingURL = httpReq.getRequestURL();
      String queryString = httpReq.getQueryString();
      if (queryString != null && queryString.length() > 0)
        receivingURL.append("?").append(httpReq.getQueryString());

      // verify the response; ConsumerManager needs to be the same
      // (static) instance used to place the authentication request
      VerificationResult verification = manager.verify(receivingURL.toString(), response, discovered);

      // examine the verification result and extract the verified
      // identifier
      Identifier verified = verification.getVerifiedId();
      if (verified != null) {
        AuthSuccess authSuccess = (AuthSuccess)verification.getAuthResponse();

        if (authSuccess.hasExtension(SRegMessage.OPENID_NS_SREG)) {
          MessageExtension ext = authSuccess.getExtension(SRegMessage.OPENID_NS_SREG);
          if (ext instanceof SRegResponse) {
            SRegResponse sregResp = (SRegResponse)ext;
            for (Iterator iter = sregResp.getAttributeNames().iterator(); iter.hasNext();) {
              String name = (String)iter.next();
              String value = sregResp.getParameterValue(name);
              httpReq.setAttribute(name, value);
            }
          }
        }
        if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
          FetchResponse fetchResp = (FetchResponse)authSuccess.getExtension(AxMessage.OPENID_NS_AX);

          // List emails = fetchResp.getAttributeValues("email");
          // String email = (String) emails.get(0);

          List aliases = fetchResp.getAttributeAliases();
          for (Iterator iter = aliases.iterator(); iter.hasNext();) {
            String alias = (String)iter.next();
            List values = fetchResp.getAttributeValues(alias);
            if (values.size() > 0) {
              httpReq.setAttribute(alias, values.get(0));
            }
          }
        }

        return verified; // success
      }
    } catch (OpenIDException e) {
      // present error to the user
    }

    return null;
  }
}
