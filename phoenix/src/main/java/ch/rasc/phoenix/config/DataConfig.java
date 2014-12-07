package ch.rasc.phoenix.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ch.rasc.phoenix.entity.BaseOption;

@Configuration
@EnableTransactionManagement
public class DataConfig {

	@Autowired
	private Environment environment;

	@Bean
	public DataSource dataSource() throws NamingException {
		Context ctx = new InitialContext();
		return (DataSource) ctx.lookup("java:comp/env/jdbc/phoenixds");
	}

	@Bean
	@DependsOn("liquibaseBean")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
			throws NamingException {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		// adapter.setShowSql(true);
		emf.setJpaVendorAdapter(adapter);

		emf.setPackagesToScan(BaseOption.class.getPackage().getName());
		return emf;
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws NamingException {
		return new JpaTransactionManager(entityManagerFactory().getObject());
	}

	@Bean
	public SpringLiquibase liquibaseBean() throws NamingException {
		SpringLiquibase bean = new SpringLiquibase();
		bean.setDataSource(dataSource());
		bean.setChangeLog("classpath:db/changelog.xml");
		return bean;
	}

}
