package ch.rasc.sqrldemo.config;

import static ch.rasc.sqrldemo.db.tables.AppUser.APP_USER;
import static ch.rasc.sqrldemo.db.tables.PreviousIdentityKey.PREVIOUS_IDENTITY_KEY;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import ch.rasc.sqrl.auth.SqrlIdentity;
import ch.rasc.sqrl.auth.SqrlIdentityService;
import ch.rasc.sqrldemo.db.tables.records.AppUserRecord;
import ch.rasc.sqrldemo.security.AppSqrlIdentity;

@Service
public class AppSqrlIdentityService implements SqrlIdentityService {

	private final DSLContext dsl;

	public AppSqrlIdentityService(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public SqrlIdentity find(String identityKey) {
		var appUserRecord = this.dsl.selectFrom(APP_USER)
				.where(APP_USER.IDENTITY_KEY.eq(identityKey)).fetchOne();
		if (appUserRecord != null) {
			return new AppSqrlIdentity(appUserRecord);
		}
		return null;
	}

	@Override
	public SqrlIdentity findInPreviousIdentityKeys(String identityKey) {
		var record = this.dsl.select(APP_USER.asterisk()).from(APP_USER)
				.innerJoin(PREVIOUS_IDENTITY_KEY).onKey()
				.where(PREVIOUS_IDENTITY_KEY.PREV_IDENTITY_KEY.eq(identityKey)).fetchOne()
				.into(AppUserRecord.class);
		if (record != null) {
			return new AppSqrlIdentity(record);
		}
		return null;
	}

	@Override
	public void newIdentity(String identityKey, String serverUnlockKey,
			String verifyUnlockKey) {
		this.dsl.insertInto(APP_USER)
				.columns(APP_USER.IDENTITY_KEY, APP_USER.SERVER_UNLOCK_KEY,
						APP_USER.VERIFY_UNLOCK_KEY, APP_USER.ENABLED)
				.values(identityKey, serverUnlockKey, verifyUnlockKey, true).execute();
	}

	@Override
	public void swap(String previousIdentityKey, String newIdentityKey) {
		this.dsl.transaction(txConf -> {
			try (var txDsl = DSL.using(txConf)) {
				var appUserRecord = txDsl.selectFrom(APP_USER)
						.where(APP_USER.IDENTITY_KEY.eq(previousIdentityKey)).fetchOne();
				if (appUserRecord != null) {
					txDsl.insertInto(PREVIOUS_IDENTITY_KEY)
							.columns(PREVIOUS_IDENTITY_KEY.PREV_IDENTITY_KEY,
									PREVIOUS_IDENTITY_KEY.APP_USER_ID)
							.values(appUserRecord.getIdentityKey(), appUserRecord.getId())
							.execute();
					txDsl.update(APP_USER).set(APP_USER.IDENTITY_KEY, newIdentityKey)
							.where(APP_USER.ID.eq(appUserRecord.getId())).execute();
				}
			}
		});
	}

	@Override
	public void disable(String identityKey) {
		this.dsl.update(APP_USER).set(APP_USER.ENABLED, false)
				.where(APP_USER.IDENTITY_KEY.eq(identityKey)).execute();
	}

	@Override
	public void enable(String identityKey) {
		this.dsl.update(APP_USER).set(APP_USER.ENABLED, true)
				.where(APP_USER.IDENTITY_KEY.eq(identityKey)).execute();
	}

	@Override
	public void remove(String identityKey) {
		this.dsl.delete(APP_USER).where(APP_USER.IDENTITY_KEY.eq(identityKey)).execute();
	}

}
