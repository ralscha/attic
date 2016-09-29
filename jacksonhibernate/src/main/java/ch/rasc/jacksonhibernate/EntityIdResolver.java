package ch.rasc.jacksonhibernate;

import javax.persistence.EntityManager;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.fasterxml.jackson.annotation.ObjectIdResolver;

public class EntityIdResolver implements ObjectIdResolver {

	private final EntityManager entityManager;

	private final Class<?> entityClass;

	private final TransactionTemplate transactionTemplate;

	public EntityIdResolver(EntityManager entityManager,
			PlatformTransactionManager transactionManager, Class<?> entityClass) {
		this.entityManager = entityManager;
		this.entityClass = entityClass;
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	@Override
	public void bindItem(IdKey id, Object pojo) {
		// nothing here
	}

	@Override
	public Object resolveId(IdKey id) {
		return this.transactionTemplate
				.execute(t -> this.entityManager.find(this.entityClass, id.key));
	}

	@Override
	public ObjectIdResolver newForDeserialization(Object context) {
		return this;
	}

	@Override
	public boolean canUseFor(ObjectIdResolver resolverType) {
		return resolverType.getClass() == getClass();
	}

}
