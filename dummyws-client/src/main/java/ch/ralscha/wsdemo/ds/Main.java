package ch.ralscha.wsdemo.ds;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class Main {

  public static void main(String[] args) {

    Authenticator.setDefault(new Authenticator() {
      @Override
      public PasswordAuthentication getPasswordAuthentication() {
        return (new PasswordAuthentication("admin", "admin".toCharArray()));
      }
    });

    DocumentServiceImplService service = new DocumentServiceImplService();
    DocumentService ds = service.getDocumentServiceImplPort();
    
    
    System.out.println(ds.saveDocument(null, null, null, true, null, null, "Doc1"));

  }

}
