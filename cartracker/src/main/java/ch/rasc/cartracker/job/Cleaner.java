package ch.rasc.cartracker.job;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.cartracker.entity.QCarImage;

import com.mysema.query.jpa.impl.JPADeleteClause;

@Component
public class Cleaner {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Deletes all CarImage entities which don't have a connection to a car
	 */
	@Transactional
	@Scheduled(cron = "0 0 4 * * *")
	public void cleanUpLeftoverImages() {
		new JPADeleteClause(entityManager, QCarImage.carImage).where(
				QCarImage.carImage.car.isNull()).execute();
	}
}
