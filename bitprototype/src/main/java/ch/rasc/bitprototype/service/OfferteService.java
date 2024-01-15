package ch.rasc.bitprototype.service;

import org.joda.time.DateTime;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableMap;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.bitprototype.entity.Offerte;
import ch.rasc.bitprototype.entity.OfferteWorkflow;
import ch.rasc.bitprototype.entity.User;
import ch.rasc.bitprototype.security.JpaUserDetails;
import ch.rasc.bitprototype.util.Util;
import ch.rasc.edsutil.BaseCRUDService;
import ch.rasc.edsutil.bean.ExtDirectStoreValidationResult;

@Service
@PreAuthorize("isAuthenticated()")
public class OfferteService extends BaseCRUDService<Offerte> {

	@Override
	@Transactional
	public ExtDirectStoreValidationResult<Offerte> create(Offerte newEntity) {
		User user = this.entityManager.find(User.class, Util.getLoggedInUserId());
		newEntity.setLieferant(user.getLieferant());
		return super.create(newEntity);
	}

	@Transactional
	@ExtDirectMethod
	public ImmutableMap<String, ?> updateStatus(Long offerteId, OfferteStatus newStatus,
			String notes) {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof JpaUserDetails) {
			JpaUserDetails userDetail = (JpaUserDetails) principal;
			Offerte offerte = this.entityManager.find(Offerte.class, offerteId);
			User user = this.entityManager.getReference(User.class,
					userDetail.getUserDbId());

			OfferteWorkflow workflow = new OfferteWorkflow();
			workflow.setCreateDate(DateTime.now());
			workflow.setOfferte(offerte);
			workflow.setUser(user);
			workflow.setLastStatus(offerte.getStatus());
			workflow.setNextStatus(newStatus);
			workflow.setNotes(notes);
			this.entityManager.persist(workflow);

			offerte.setStatus(newStatus);
			return ImmutableMap.of("status", offerte.getStatus());
		}

		return null;
	}

	// @Autowired
	// private MessageSource messageSource;
	//
	// @Override
	// @ExtDirectMethod(value = ExtDirectMethodType.STORE_READ)
	// @Transactional(readOnly = true)
	// public ExtDirectStoreResult<Lieferant> read(ExtDirectStoreReadRequest
	// request) {
	//
	// JPQLQuery query = new JPAQuery(entityManager).from(QLieferant.lieferant);
	// if (!request.getFilters().isEmpty()) {
	// StringFilter filter = (StringFilter)
	// request.getFilters().iterator().next();
	//
	// BooleanBuilder bb = new BooleanBuilder();
	// bb.or(QLieferant.lieferant.firma.contains(filter.getValue()));
	//
	// query.where(bb);
	// }
	//
	// Util.addPagingAndSorting(query, request, Lieferant.class,
	// QLieferant.lieferant);
	// SearchResults<Lieferant> searchResult =
	// query.listResults(QLieferant.lieferant);
	//
	// return new ExtDirectStoreResult<>(searchResult.getTotal(),
	// searchResult.getResults());
	// }

}
