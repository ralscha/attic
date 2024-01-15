package ch.rasc.bitprototype.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysema.query.jpa.impl.JPAQuery;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.rasc.bitprototype.entity.BedarfWorkflow;
import ch.rasc.bitprototype.entity.OfferteWorkflow;
import ch.rasc.bitprototype.entity.QBedarfWorkflow;
import ch.rasc.bitprototype.entity.QOfferteWorkflow;

@Service
public class WorkflowService {

	@PersistenceContext
	private EntityManager entityManager;

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ)
	@Transactional(readOnly = true)
	public List<BedarfWorkflow> readBedarf(
			@RequestParam(value = "bedarfId") Long bedarfId) {
		return new JPAQuery(this.entityManager).from(QBedarfWorkflow.bedarfWorkflow)
				.where(QBedarfWorkflow.bedarfWorkflow.bedarf.id.eq(bedarfId))
				.list(QBedarfWorkflow.bedarfWorkflow);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ)
	@Transactional(readOnly = true)
	public List<OfferteWorkflow> readOfferte(
			@RequestParam(value = "offerteId") Long offerteId) {
		return new JPAQuery(this.entityManager).from(QOfferteWorkflow.offerteWorkflow)
				.where(QOfferteWorkflow.offerteWorkflow.offerte.id.eq(offerteId))
				.list(QOfferteWorkflow.offerteWorkflow);
	}

}
