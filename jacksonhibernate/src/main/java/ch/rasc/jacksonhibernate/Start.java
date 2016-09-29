package ch.rasc.jacksonhibernate;

import javax.persistence.EntityManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.SpringHandlerInstantiator;
import org.springframework.transaction.PlatformTransactionManager;

import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;

@SpringBootApplication
public class Start {

	@Bean
	public ObjectMapper objectMapper(ApplicationContext ctx, EntityManager entityManager,
			PlatformTransactionManager transactionManager) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		objectMapper.setHandlerInstantiator(
				new SpringHandlerInstantiator(ctx.getAutowireCapableBeanFactory()) {
					@Override
					public ObjectIdResolver resolverIdGeneratorInstance(
							MapperConfig<?> config, Annotated annotated,
							Class<?> implClass) {
						if (implClass == EntityIdResolver.class) {
							System.out.println(annotated.getRawType());
							return new EntityIdResolver(entityManager, transactionManager,
									annotated.getRawType());
						}
						return null;
					}

				});

		// objectMapper.registerModule(hibernate4Module());

		return objectMapper;
	}

	// public Hibernate4Module hibernate4Module() {
	// Hibernate4Module hibernate4Module = new Hibernate4Module();
	// hibernate4Module.enable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);
	// //hibernate4Module.disable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);
	// return hibernate4Module;
	// }

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Start.class, args);
	}
}
