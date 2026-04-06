package ch.rasc.wsdemo;

import java.net.MalformedURLException;
import java.net.URL;

import ch.rasc.wsdemo.calculator.Calculator;
import ch.rasc.wsdemo.calculator.CalculatorService;
import ch.rasc.wsdemo.helloworld.HelloWorldImpl;
import ch.rasc.wsdemo.helloworld.HelloWorldImplService;

public class Main {

	public static void main(String[] args) throws MalformedURLException {

		// <JDK_HOME>\bin\wsimport.exe -s main\java -Xnocompile -p
		// ch.rasc.wsdemo.calculator http://localhost:8080/cxf/Calculator?wsdl
		HelloWorldImplService service = new HelloWorldImplService(
				new URL("http://localhost:8080/cxf/HelloWorld?wsdl"));
		HelloWorldImpl helloWorld = service.getHelloWorldImplPort();
		String msgFromServer = helloWorld.sayHi("John");
		System.out.println(msgFromServer);

		CalculatorService calcService = new CalculatorService(
				new URL("http://localhost:8080/cxf/Calculator?wsdl"));
		Calculator calc = calcService.getCalculatorPort();
		System.out.println(calc.add(10, 20)); // returns 30
		System.out.println(calc.divide(20, 10)); // return 2
		System.out.println(calc.multiply(2, 40)); // return 80
		System.out.println(calc.subtract(40, 10)); // return 30
	}

}
