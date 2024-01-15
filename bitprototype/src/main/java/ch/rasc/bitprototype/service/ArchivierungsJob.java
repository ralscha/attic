package ch.rasc.bitprototype.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import ch.rasc.bitprototype.entity.Bedarf;
import ch.rasc.bitprototype.entity.QBedarf;

@Component
public class ArchivierungsJob {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Scheduled(cron = "0 0 2 * * *")
	public void doCleanup() {

		LocalDate tenDaysAgo = LocalDate.now().minusDays(10);

		for (Bedarf be : new JPAQuery(this.entityManager).from(QBedarf.bedarf)
				.where(QBedarf.bedarf.abgeschlossen.before(tenDaysAgo))
				.list(QBedarf.bedarf)) {
			be.setStatus(BedarfStatus.ARCHIVIERT);
		}

	}

}
