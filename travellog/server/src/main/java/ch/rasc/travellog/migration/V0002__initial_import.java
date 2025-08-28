package ch.rasc.travellog.migration;

import static ch.rasc.travellog.db.tables.AppUser.APP_USER;
import static org.jooq.impl.DSL.using;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ch.rasc.travellog.Application;

@Component
public class V0002__initial_import extends BaseJavaMigration {

	private final PasswordEncoder passwordEncoder;

	public V0002__initial_import(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void migrate(Context context) {

		@SuppressWarnings("resource")
		DSLContext dsl = using(context.getConnection());

		dsl.transaction(txConf -> {
			var txdsl = DSL.using(txConf);
			try {
				txdsl.insertInto(APP_USER, APP_USER.EMAIL, APP_USER.PASSWORD_HASH,
						APP_USER.AUTHORITY, APP_USER.ENABLED, APP_USER.EXPIRED,
						APP_USER.LAST_ACCESS, APP_USER.CONFIRMATION_TOKEN,
						APP_USER.CONFIRMATION_TOKEN_CREATED,
						APP_USER.PASSWORD_RESET_TOKEN,
						APP_USER.PASSWORD_RESET_TOKEN_CREATED)
						.values("admin@test.com", this.passwordEncoder.encode("password"),
								"ADMIN", true, null, null, null, null, null, null)
						.values("user@test.com", this.passwordEncoder.encode("password"),
								"USER", true, null, null, null, null, null, null)
						.execute();
			}
			catch (Exception e) {
				Application.log.error("database migration", e);
			}

		});
	}

}