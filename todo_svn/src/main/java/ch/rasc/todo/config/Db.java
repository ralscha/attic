package ch.rasc.todo.config;

import javax.persistence.EntityManager;

import org.javers.core.Javers;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.jpa.properties.hibernate")
@Component
public class Db extends com.querydsl.jpa.impl.JPAQueryFactory {

	private final EntityManager entityManager;
	
	private String dialect;

	private final Javers javers;

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public Db(EntityManager entityManager, Javers javers) {
		super(entityManager);
		this.entityManager = entityManager;
		this.javers = javers;
	}

	public EntityManager em() {
		return this.entityManager;
	}

	public Javers javers() {
		return this.javers;
	}

	public boolean isSqlServer() {
		return dialect.contains("SQLServer");
	}
}
