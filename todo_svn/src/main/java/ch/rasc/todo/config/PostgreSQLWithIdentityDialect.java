package ch.rasc.todo.config;

import org.hibernate.dialect.PostgreSQL94Dialect;
import org.hibernate.id.IdentityGenerator;

public class PostgreSQLWithIdentityDialect extends PostgreSQL94Dialect {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getNativeIdentifierGeneratorClass() {
		return IdentityGenerator.class;
	}

}
