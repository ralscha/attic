package ch.rasc.jooq;

import javax.sql.DataSource;

import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public DefaultDSLContext dsl(ExceptionTranslator exceptionTranslator,
			SpringTransactionProvider transactionProvider,
			DataSourceConnectionProvider dataSourceConnectionProvider) {
		org.jooq.Configuration config = new DefaultConfiguration()
				.set(dataSourceConnectionProvider).set(transactionProvider)
				.set(new DefaultExecuteListenerProvider(exceptionTranslator))
				.set(SQLDialect.MYSQL);
		return new DefaultDSLContext(config);
	}

	@Bean
	public DataSourceConnectionProvider dataSourceConnectionProvider(
			DataSource dataSource) {
		return new DataSourceConnectionProvider(
				new TransactionAwareDataSourceProxy(dataSource));
	}
}
