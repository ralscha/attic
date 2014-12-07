package ch.rasc.phoenix.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.phoenix.entity.Probability;
import ch.rasc.phoenix.entity.QProbability;

import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class ProbabilityService {

	@PersistenceContext
	private EntityManager entityManager;

	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	public List<Probability> read() {
		return new JPAQuery(entityManager).from(QProbability.probability)
				.orderBy(QProbability.probability.id.asc())
				.list(QProbability.probability);
	}

}
