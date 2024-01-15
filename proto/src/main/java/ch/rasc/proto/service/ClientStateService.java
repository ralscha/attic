package ch.rasc.proto.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.proto.config.DbManager;
import ch.rasc.proto.config.security.AppUserDetails;
import ch.rasc.proto.entity.ClientState;

@Service
@PreAuthorize("isAuthenticated()")
public class ClientStateService {

	private final DbManager dbManager;

	@Autowired
	public ClientStateService(DbManager dbManager) {
		this.dbManager = dbManager;
	}

	@ExtDirectMethod(STORE_READ)
	public List<ClientState> read(@AuthenticationPrincipal AppUserDetails jpaUserDetails) {
		return this.dbManager.runInTx(db -> {
			return DbManager.getAll(db, ClientState.class).stream()
					.filter(cs -> cs.getUserId() == jpaUserDetails.getUserDbId())
					.collect(Collectors.toList());

		});

	}

	@ExtDirectMethod(STORE_MODIFY)
	public ExtDirectStoreResult<ClientState> destroy(
			@AuthenticationPrincipal AppUserDetails jpaUserDetails,
			List<ClientState> states) {

		Set<Long> csIds = states.stream().map(ClientState::getId)
				.collect(Collectors.toSet());
		this.dbManager.runInTxWithoutResult(db -> {
			DbManager.getAll(db, ClientState.class).stream()
					.filter(cs -> cs.getUserId() == jpaUserDetails.getUserDbId())
					.filter(cs -> csIds.contains(cs.getId()))
					.forEach(cs -> DbManager.remove(db, cs));

		});

		ExtDirectStoreResult<ClientState> result = new ExtDirectStoreResult<>();
		result.setSuccess(true);
		return result;
	}

	@ExtDirectMethod(STORE_MODIFY)
	public List<ClientState> create(
			@AuthenticationPrincipal AppUserDetails jpaUserDetails,
			List<ClientState> states) {

		this.dbManager.runInTxWithoutResult(db -> {
			for (ClientState state : states) {
				state.setId(null);
				state.setUserId(jpaUserDetails.getUserDbId());
				DbManager.put(db, state);
			}
		});

		return states;
	}

	@ExtDirectMethod(STORE_MODIFY)
	public List<ClientState> update(
			@AuthenticationPrincipal AppUserDetails jpaUserDetails,
			List<ClientState> states) {

		this.dbManager.runInTxWithoutResult(db -> {
			for (ClientState state : states) {
				state.setUserId(jpaUserDetails.getUserDbId());
				DbManager.put(db, state);
			}
		});

		return states;
	}

}
