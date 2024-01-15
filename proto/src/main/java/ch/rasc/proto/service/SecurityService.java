package ch.rasc.proto.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.proto.config.DbManager;
import ch.rasc.proto.config.security.AppUserDetails;
import ch.rasc.proto.entity.PersistentLogin;
import ch.rasc.proto.entity.User;

@Service
public class SecurityService {

	private final DbManager dbManager;

	@Autowired
	public SecurityService(DbManager dbManager) {
		this.dbManager = dbManager;
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	public User getLoggedOnUser(@AuthenticationPrincipal AppUserDetails jpaUserDetails) {

		if (jpaUserDetails != null) {
			User user = jpaUserDetails.getUser(this.dbManager);
			if (jpaUserDetails.hasRole("ADMIN")) {
				user.setAutoOpenView("Proto.view.gantt.View");
			}
			else if (jpaUserDetails.hasRole("USER")) {
				user.setAutoOpenView("Proto.view.gantt.View");
			}
			return user;
		}

		return null;
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	public List<PersistentLogin> readPersistentLogins(
			@AuthenticationPrincipal AppUserDetails jpaUserDetails) {

		return this.dbManager.getAll(PersistentLogin.class).stream()
				.filter(p -> p.getUserId().equals(jpaUserDetails.getUserDbId()))
				.collect(Collectors.toList());

	}

	@ExtDirectMethod(STORE_MODIFY)
	@PreAuthorize("isAuthenticated()")
	public void destroyPersistentLogin(String series,
			@AuthenticationPrincipal AppUserDetails jpaUserDetails) {
		this.dbManager.runInTxWithoutResult(db -> {
			PersistentLogin pl = DbManager.get(db, PersistentLogin.class, series);
			if (pl.getUserId().equals(jpaUserDetails.getUserDbId())) {
				db.getTreeMap(PersistentLogin.class.getName()).remove(series);
			}
		});
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	public boolean switchUser(Long userId) {
		User switchToUser = this.dbManager.runInTx(db -> DbManager.get(db, User.class,
				userId));
		if (switchToUser != null) {

			AppUserDetails principal = new AppUserDetails(switchToUser);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					principal, null, principal.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(token);

			return true;
		}

		return false;
	}

}
