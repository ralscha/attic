package ch.rasc.bitprototype.service;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableMap;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.bitprototype.entity.Bedarf;
import ch.rasc.bitprototype.entity.BedarfWorkflow;
import ch.rasc.bitprototype.entity.QBedarf;
import ch.rasc.bitprototype.entity.Role;
import ch.rasc.bitprototype.entity.User;
import ch.rasc.bitprototype.security.JpaUserDetails;
import ch.rasc.bitprototype.util.Util;
import ch.rasc.edsutil.BaseCRUDService;
import ch.rasc.edsutil.QueryUtil;
import ch.rasc.edsutil.bean.ExtDirectStoreValidationResult;

@Service
@PreAuthorize("isAuthenticated()")
public class BedarfService extends BaseCRUDService<Bedarf> {

	@Override
	public ExtDirectStoreResult<Bedarf> read(ExtDirectStoreReadRequest request) {
		JPQLQuery query = new JPAQuery(this.entityManager).from(QBedarf.bedarf);

		if (Util.userInRole(Role.BEDARF)) {
			User user = this.entityManager.find(User.class, Util.getLoggedInUserId());
			query.where(QBedarf.bedarf.owner.eq(user));
			query.where(QBedarf.bedarf.status.notIn(BedarfStatus.ARCHIVIERT));
		}
		else if (Util.userInRole(Role.EINKAUF)) {
			query.where(QBedarf.bedarf.status.in(BedarfStatus.BEDARF,
					BedarfStatus.ANFRAGE, BedarfStatus.ABGESCHLOSSEN));
		}
		else if (Util.userInRole(Role.LIEFERANT)) {
			query.where(QBedarf.bedarf.status.in(BedarfStatus.ANFRAGE,
					BedarfStatus.ABGESCHLOSSEN));
		}

		QueryUtil.addPagingAndSorting(query, request, Bedarf.class, QBedarf.bedarf);
		SearchResults<Bedarf> result = query.listResults(QBedarf.bedarf);
		return new ExtDirectStoreResult<>(result.getTotal(), result.getResults());
	}

	@Override
	@Transactional
	public ExtDirectStoreValidationResult<Bedarf> create(Bedarf newEntity) {
		newEntity.setOwner(
				this.entityManager.getReference(User.class, Util.getLoggedInUserId()));
		ExtDirectStoreValidationResult<Bedarf> result = super.create(newEntity);

		BedarfWorkflow workflow = new BedarfWorkflow();
		workflow.setCreateDate(DateTime.now());
		workflow.setBedarf(newEntity);
		workflow.setUser(Util.getLoggedInUser(this.entityManager));
		workflow.setLastStatus(null);
		workflow.setNextStatus(BedarfStatus.ENTWURF);
		workflow.setNotes(null);
		this.entityManager.persist(workflow);

		return result;
	}

	@Transactional
	@ExtDirectMethod
	public ImmutableMap<String, ?> updateStatus(Long bedarfId, BedarfStatus newStatus,
			String notes) {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof JpaUserDetails) {
			JpaUserDetails userDetail = (JpaUserDetails) principal;
			Bedarf bedarf = this.entityManager.find(Bedarf.class, bedarfId);
			User user = this.entityManager.getReference(User.class,
					userDetail.getUserDbId());

			BedarfWorkflow workflow = new BedarfWorkflow();
			workflow.setCreateDate(DateTime.now());
			workflow.setBedarf(bedarf);
			workflow.setUser(user);
			workflow.setLastStatus(bedarf.getStatus());
			workflow.setNextStatus(newStatus);
			workflow.setNotes(notes);
			this.entityManager.persist(workflow);

			bedarf.setStatus(newStatus);

			if (newStatus == BedarfStatus.ABGESCHLOSSEN) {
				bedarf.setAbgeschlossen(LocalDate.now());
			}

			return ImmutableMap.of("status", bedarf.getStatus());
		}

		return null;
	}

}
