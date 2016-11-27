package ch.ralscha.wsdemo;

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

    HelloWorldImplService service = new HelloWorldImplService();
    HelloWorld helloWorld = service.getHelloWorldImplPort();

    System.out.println(helloWorld.sayHi("hi"));

  }

}
