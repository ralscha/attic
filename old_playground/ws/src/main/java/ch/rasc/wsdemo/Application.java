package ch.rasc.wsdemo;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.spring.EndpointDefinitionParser.SpringEndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml",
		"classpath:META-INF/cxf/cxf-servlet.xml" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ServletRegistrationBean cxfServlet(Bus bus) {
		CXFServlet cxfServlet = new CXFServlet();
		cxfServlet.setBus(bus);
		ServletRegistrationBean reg = new ServletRegistrationBean(cxfServlet, "/cxf/*");
		reg.setLoadOnStartup(1);
		return reg;
	}

	@Bean
	public Endpoint calc(Bus bus, Calculator calculator) {
		SpringEndpointImpl endpoint = new SpringEndpointImpl(bus, calculator);
		endpoint.setAddress("/Calculator");
		endpoint.publish();
		return endpoint;
	}

	@Bean
	public Endpoint helloWorld(Bus bus, HelloWorldImpl helloWorldImpl) {
		SpringEndpointImpl endpoint = new SpringEndpointImpl(bus, helloWorldImpl);
		endpoint.setAddress("/HelloWorld");
		endpoint.publish();
		return endpoint;
	}

}
