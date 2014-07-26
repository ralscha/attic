package ch.rasc.e4ds.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.data.convert.ConfigurableTypeInformationMapper;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import ch.rasc.e4ds.domain.AccessLog;
import ch.rasc.e4ds.domain.LoggingEvent;
import ch.rasc.e4ds.domain.User;
import ch.rasc.e4ds.util.DateTimeToDateConverter;
import ch.rasc.e4ds.util.DateToDateTimeConverter;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

@Configuration
public class DataConfig extends AbstractMongoConfiguration {

	private static ImmutableMap<Class<?>, String> sourceTypeMap;

	public DataConfig() {
		ImmutableMap.Builder<Class<?>, String> builder = ImmutableMap.builder();

		builder.put(AccessLog.class, "1");
		builder.put(LoggingEvent.class, "2");
		builder.put(User.class, "3");

		sourceTypeMap = builder.build();
	}

	@Autowired
	private Environment environment;

	@Override
	public Mongo mongo() throws Exception {

		String host;
		int port;

		if (environment.containsProperty("mongoServer")) {
			host = environment.getProperty("mongoServer");
		} else {
			host = "127.0.0.1";
		}

		if (environment.containsProperty("mongoPort")) {
			port = environment.getProperty("mongoPort", Integer.class);
		} else {
			port = 27017;
		}

		MongoOptions options = new MongoOptions();
		options.safe = true;

		ServerAddress address = new ServerAddress(host, port);
		return new Mongo(address, options);
	}

	@Override
	public String getDatabaseName() {
		return "e4ds";
	}

	@Override
	@Bean
	public CustomConversions customConversions() {
		List<Converter<?, ?>> converters = Lists.newArrayList();
		converters.add(DateTimeToDateConverter.INSTANCE);
		converters.add(DateToDateTimeConverter.INSTANCE);
		return new CustomConversions(converters);
	}

	@Override
	@Bean
	public MongoMappingContext mongoMappingContext() throws ClassNotFoundException {
		MongoMappingContext mappingContext = new MongoMappingContext();
		mappingContext.setInitialEntitySet(sourceTypeMap.keySet());
		mappingContext.setSimpleTypeHolder(customConversions().getSimpleTypeHolder());
		mappingContext.initialize();

		return mappingContext;
	}

	@Override
	@Bean
	public MappingMongoConverter mappingMongoConverter() throws Exception {
		MappingMongoConverter converter = new MappingMongoConverter(mongoDbFactory(), mongoMappingContext());
		converter.setCustomConversions(customConversions());

		ConfigurableTypeInformationMapper typeInformationMapper = new ConfigurableTypeInformationMapper(sourceTypeMap);

		DefaultMongoTypeMapper typeMapper = new DefaultMongoTypeMapper("_c", Lists.newArrayList(typeInformationMapper));
		converter.setTypeMapper(typeMapper);

		return converter;
	}

}
