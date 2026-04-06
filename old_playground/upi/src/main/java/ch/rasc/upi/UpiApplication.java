package ch.rasc.upi;

import javax.xml.ws.BindingProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ch.admin.ws.zas.regcent.upi._0.UPIQueryService;
import ch.admin.ws.zas.regcent.upi._0.UPIQueryServicePortType;

@SpringBootApplication
public class UpiApplication {

	public static void main(String[] args) {
		
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
		
		SpringApplication.run(UpiApplication.class, args);
	}
	
	@Bean
	public UPIQueryServicePortType getQueryService() {

		UPIQueryService upiQueryService = new UPIQueryService();

		UPIQueryServicePortType portType = upiQueryService.getUPIQueryServicePort();

		((BindingProvider) portType).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				"https://www.wupi-test.zas.admin.ch/wupi/UPIQueryService");
		((BindingProvider) portType).getRequestContext()
				.put(BindingProvider.USERNAME_PROPERTY, "ZUP17263");
		((BindingProvider) portType).getRequestContext()
				.put(BindingProvider.PASSWORD_PROPERTY, "079Om3SO");

		return portType;
	}
}
