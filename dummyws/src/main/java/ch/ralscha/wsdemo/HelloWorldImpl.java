package ch.ralscha.wsdemo;

import javax.jws.WebService;

@WebService(endpointInterface = "ch.ralscha.wsdemo.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

  public String sayHi(String text) {
    System.out.println("sayHi called");
    return "Hello " + text;
  }
}