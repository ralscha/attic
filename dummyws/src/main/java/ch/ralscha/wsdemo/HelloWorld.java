package ch.ralscha.wsdemo;

import javax.jws.WebService;

@WebService
public interface HelloWorld {
  String sayHi(String text);
}