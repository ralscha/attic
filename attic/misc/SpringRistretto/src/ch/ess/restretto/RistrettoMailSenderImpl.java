package ch.ess.restretto;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.columba.ristretto.composer.MimeTreeRenderer;
import org.columba.ristretto.io.CharSequenceSource;
import org.columba.ristretto.log.RistrettoLogger;
import org.columba.ristretto.message.Address;
import org.columba.ristretto.message.BasicHeader;
import org.columba.ristretto.message.Header;
import org.columba.ristretto.message.LocalMimePart;
import org.columba.ristretto.message.MimeHeader;
import org.columba.ristretto.message.MimeType;
import org.columba.ristretto.parser.AddressParser;
import org.columba.ristretto.parser.ParserException;
import org.columba.ristretto.smtp.SMTPException;
import org.columba.ristretto.smtp.SMTPProtocol;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.StringUtils;

/**
 * @author sr
 */
public class RistrettoMailSenderImpl implements MailSender {

  public static final int DEFAULT_PORT = -1;
  
  private String host;
  private boolean debug;
  private int port = DEFAULT_PORT;
  private String username;
  private char[] password;
  private String authAlgorithm = "PLAIN"; //PLAIN, LOGIN, DIGEST-MD5

  /**
   * Set the SMTP mail host.
   */
  public void setHost(String host) {
    this.host = host;
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }
     
  public void setPort(int port) {
    this.port = port;
  }
      
  public void setAuthAlgorithm(String authAlgorithm) {
    this.authAlgorithm = authAlgorithm;
  }

  public void setPassword(String password) {
    this.password = password.toCharArray();
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void send(SimpleMailMessage simpleMessage) throws MailException {
    send(new SimpleMailMessage[]{simpleMessage});

  }

  public void send(SimpleMailMessage[] simpleMessages) throws MailException {

    if (debug) {
      RistrettoLogger.setLogStream(System.out);
    }

    Map failedMessages = new HashMap();

    for (int i = 0; i < simpleMessages.length; i++) {
      SimpleMailMessage simpleMessage = simpleMessages[i];

      Header header = new Header();
      BasicHeader basicHeader = new BasicHeader(header);

      Address fromAddress = createAddress(simpleMessage.getFrom());
      basicHeader.setFrom(fromAddress);

      List addressList = new ArrayList();
      
      Address[] toAddress = createAddress(simpleMessage.getTo());
      addToAddressList(addressList, toAddress);
      basicHeader.setTo(toAddress);

      if (simpleMessage.getCc() != null) {
        Address[] ccAddress = createAddress(simpleMessage.getCc());
        addToAddressList(addressList, ccAddress);
        basicHeader.setCc(ccAddress);
      }

      if (simpleMessage.getBcc() != null) {
        Address[] bccAddress = createAddress(simpleMessage.getBcc());
        addToAddressList(addressList, bccAddress);
        //basicHeader.setBcc(bccAddress);
      }

      if (simpleMessage.getReplyTo() != null) {
        basicHeader.setReplyTo(new Address[]{createAddress(simpleMessage.getReplyTo())});
      }

      if (simpleMessage.getSentDate() != null) {
        basicHeader.setDate(simpleMessage.getSentDate());
      }

      basicHeader.setSubject(simpleMessage.getSubject(), Charset.forName("ISO-8859-1"));
      basicHeader.set("X-Mailer", "Spring / Ristretto");

      MimeHeader mimeHeader = new MimeHeader(header);
      mimeHeader.set("Mime-Version", "1.0");
     
      LocalMimePart rootMimePart = new LocalMimePart(mimeHeader);
      
      MimeHeader textHeader = rootMimePart.getHeader();   
      
      textHeader.setMimeType(new MimeType("text", "plain"));      
      textHeader.putContentParameter("charset", "ISO-8859-1");
      rootMimePart.setBody(new CharSequenceSource(simpleMessage.getText()));

      
      SMTPProtocol protocol;
      
      if (port == DEFAULT_PORT) {
        protocol = new SMTPProtocol(host);
      } else {
        protocol = new SMTPProtocol(host, port);
      }

      
      
      try {

        // Open the port
        protocol.openPort();
        
        // Authorize if neccessary
        if (StringUtils.hasText(username)) {
          protocol.auth(authAlgorithm, username, password);
        }        

        // The EHLO command 
        protocol.ehlo(InetAddress.getLocalHost());

        // Setup from and recipient
        protocol.mail(fromAddress);
        
        
        for (Iterator it = addressList.iterator(); it.hasNext();) {
          Address address = (Address)it.next();
          protocol.rcpt(address);
        }
        

        // Finally we render the message to an inputstream
        InputStream messageSource = MimeTreeRenderer.getInstance().renderMimePart(rootMimePart);

        // Finally send the data
        protocol.data(messageSource);

        // And close the session
        protocol.quit();
        
        messageSource.close();
        
      } catch (SMTPException e) {
        throw new MailSendException(e.getMessage());
      } catch (IOException e) {
        throw new MailSendException(e.getMessage());
      } catch (Exception e) {
        throw new MailSendException(e.getMessage());
      }

    }

    if (!failedMessages.isEmpty()) {
      throw new MailSendException(failedMessages);
    }
  }

  private void addToAddressList(List addressList, Address[] addresses) {
    if (addresses != null) {
      addressList.addAll(Arrays.asList(addresses));
    }
    
  }

  private Address createAddress(String addressString) throws MailParseException {
    Address[] addressArray = createAddress(new String[]{addressString});
    if (addressArray != null) {
      return addressArray[0];
    }
    return null;
  }

  private Address[] createAddress(String[] addressStrings) throws MailParseException {

    if (addressStrings == null) {
      return null;
    }

    try {
      Address[] address = new Address[addressStrings.length];
      for (int i = 0; i < addressStrings.length; i++) {
        address[i] = AddressParser.parseAddress(addressStrings[i]);
      }
      return address;
    } catch (ParserException e) {
      throw new MailParseException("Invalid address: " + e.getSource());
    }
  }

}
