package ch.rasc.eds.starter.config;

import org.springframework.context.annotation.Configuration;

import ch.rasc.wampspring.config.AbstractWampConfigurer;
import ch.rasc.wampspring.config.EnableWamp;
import ch.rasc.wampspring.config.WampEndpointRegistry;

@Configuration
@EnableWamp
public class WampConfig extends AbstractWampConfigurer {

	@Override
	public void registerWampEndpoints(WampEndpointRegistry registry) {
		registry.addEndpoint("/wamp").withSockJS();
	}

}