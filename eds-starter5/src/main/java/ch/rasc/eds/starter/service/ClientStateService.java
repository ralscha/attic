package ch.rasc.eds.starter.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.eds.starter.config.security.JpaUserDetails;
import ch.rasc.eds.starter.entity.ClientState;
import ch.rasc.eds.starter.entity.QClientState;
import ch.rasc.edsutil.JPAQueryFactory;

@Service
@PreAuthorize("isAuthenticated()")
public class ClientStateService {

	private final JPAQueryFactory jpaQueryFactory;

	@Autowired
	public ClientStateService(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	public List<ClientState> read(
			@AuthenticationPrincipal JpaUserDetails jpaUserDetails) {
		return this.jpaQueryFactory.selectFrom(QClientState.clientState)
				.where(QClientState.clientState.userId.eq(jpaUserDetails.getUserDbId()))
				.fetch();
	}

	@ExtDirectMethod(STORE_MODIFY)
	@Transactional
	public ExtDirectStoreResult<ClientState> destroy(
			@AuthenticationPrincipal JpaUserDetails jpaUserDetails,
			List<ClientState> states) {

		for (ClientState state : states) {
			this.jpaQueryFactory.delete(QClientState.clientState)
					.where(QClientState.clientState.userId
							.eq(jpaUserDetails.getUserDbId())
							.and(QClientState.clientState.id.eq(state.getId())))
					.execute();
		}

		ExtDirectStoreResult<ClientState> result = new ExtDirectStoreResult<>();
		result.setSuccess(true);
		return result;
	}

	@ExtDirectMethod(STORE_MODIFY)
	@Transactional
	public List<ClientState> create(
			@AuthenticationPrincipal JpaUserDetails jpaUserDetails,
			List<ClientState> states) {

		for (ClientState state : states) {
			state.setId(null);
			state.setUserId(jpaUserDetails.getUserDbId());
			this.jpaQueryFactory.getEntityManager().persist(state);
		}

		return states;
	}

	@ExtDirectMethod(STORE_MODIFY)
	@Transactional
	public List<ClientState> update(
			@AuthenticationPrincipal JpaUserDetails jpaUserDetails,
			List<ClientState> states) {

		for (ClientState state : states) {
			state.setUserId(jpaUserDetails.getUserDbId());
			this.jpaQueryFactory.getEntityManager().merge(state);
		}

		return states;
	}

}
