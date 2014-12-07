package ch.rasc.phoenix.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.phoenix.entity.AffectedItem;
import ch.rasc.phoenix.entity.Probability;
import ch.rasc.phoenix.entity.QScenario;
import ch.rasc.phoenix.entity.QScenarioItem;
import ch.rasc.phoenix.entity.RevenueImpact;
import ch.rasc.phoenix.entity.Scenario;
import ch.rasc.phoenix.entity.ScenarioItem;

import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class ScenarioService {

	@PersistenceContext
	private EntityManager entityManager;

	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	public List<Scenario> read() {
		List<Scenario> list = new JPAQuery(entityManager).from(QScenario.scenario)
				.leftJoin(QScenario.scenario.probability).fetch()
				.leftJoin(QScenario.scenario.scenarioItems, QScenarioItem.scenarioItem)
				.fetch().leftJoin(QScenarioItem.scenarioItem.affectedItem).fetch()
				.leftJoin(QScenarioItem.scenarioItem.revenueImpact).fetch()
				.list(QScenario.scenario);
		return list;
	}

	@ExtDirectMethod(STORE_MODIFY)
	@Transactional
	public Scenario create(Scenario newScenario) {
		preUpdate(newScenario);
		entityManager.persist(newScenario);
		return newScenario;
	}

	@ExtDirectMethod(STORE_MODIFY)
	@Transactional
	public Scenario update(Scenario updatedScenario) {
		preUpdate(updatedScenario);
		Scenario mergedScenario = entityManager.merge(updatedScenario);
		mergedScenario.setProbabilityId(updatedScenario.getProbabilityId());
		return mergedScenario;
	}

	@ExtDirectMethod(STORE_MODIFY)
	@Transactional
	public void destroy(long id) {
		new JPADeleteClause(entityManager, QScenarioItem.scenarioItem).where(
				QScenarioItem.scenarioItem.scenario.id.eq(id)).execute();
		new JPADeleteClause(entityManager, QScenario.scenario).where(
				QScenario.scenario.id.eq(id)).execute();
	}

	private void preUpdate(Scenario newScenario) {
		if (newScenario.getProbabilityId() != null) {
			newScenario.setProbability(entityManager.getReference(Probability.class,
					newScenario.getProbabilityId()));
		}
		else {
			newScenario.setProbability(null);
		}

		if (newScenario.getScenarioItems() != null) {
			for (ScenarioItem item : newScenario.getScenarioItems()) {
				if (item.getAffectedItemId() != null) {
					item.setAffectedItem(entityManager.getReference(AffectedItem.class,
							item.getAffectedItemId()));
				}
				else {
					item.setAffectedItem(null);
				}

				if (item.getRevenueImpactId() != null) {
					item.setRevenueImpact(entityManager.getReference(RevenueImpact.class,
							item.getRevenueImpactId()));
				}
				else {
					item.setRevenueImpact(null);
				}

				item.setScenario(newScenario);
			}
		}
	}

}
