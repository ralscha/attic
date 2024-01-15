package ch.rasc.proto.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import ch.rasc.proto.config.DbManager;
import ch.rasc.proto.entity.User;

@Component
public class UserAuthenticationSuccessfulHandler implements
		ApplicationListener<InteractiveAuthenticationSuccessEvent> {

	private final DbManager dbManager;

	@Autowired
	public UserAuthenticationSuccessfulHandler(DbManager dbManager) {
		this.dbManager = dbManager;
	}

	@Override
	public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
		Object principal = event.getAuthentication().getPrincipal();
		if (principal instanceof AppUserDetails) {

			this.dbManager.runInTxWithoutResult(db -> {
				User user = DbManager.get(db, User.class,
						((AppUserDetails) principal).getUserDbId());
				user.setLockedOutUntil(null);
				user.setFailedLogins(null);
				DbManager.put(db, user);
			});
		}
	}
}