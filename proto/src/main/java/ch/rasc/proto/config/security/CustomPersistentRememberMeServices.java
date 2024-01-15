package ch.rasc.proto.config.security;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import ch.rasc.proto.config.DbManager;
import ch.rasc.proto.entity.PersistentLogin;
import ch.rasc.proto.entity.User;

/**
 * Copy of the CustomPersistentRememberMeServices class from the <a
 * href="https://jhipster.github.io/">JHipster</a> project
 *
 * Custom implementation of Spring Security's RememberMeServices.
 * <p/>
 * Persistent tokens are used by Spring Security to automatically log in users.
 * <p/>
 * This is a specific implementation of Spring Security's remember-me authentication, but
 * it is much more powerful than the standard implementations:
 * <ul>
 * <li>It allows a user to see the list of his currently opened sessions, and invalidate
 * them</li>
 * <li>It stores more information, such as the IP address and the user agent, for audit
 * purposes
 * <li>
 * <li>When a user logs out, only his current session is invalidated, and not all of his
 * sessions</li>
 * </ul>
 * <p/>
 * This is inspired by:
 * <ul>
 * <li><a href="http://jaspan.com/improved_persistent_login_cookie_best_practice">Improved
 * Persistent Login Cookie Best Practice</a></li>
 * <li><a href="https://github.com/blog/1661-modeling-your-app-s-user-session">Github's
 * "Modeling your App's User Session"</a></li></li>
 * </ul>
 * <p/>
 * The main algorithm comes from Spring Security's PersistentTokenBasedRememberMeServices,
 * but this class couldn't be cleanly extended.
 * <p/>
 */
@Component
public class CustomPersistentRememberMeServices extends AbstractRememberMeServices {

	private final Logger log = LoggerFactory
			.getLogger(CustomPersistentRememberMeServices.class);

	private final SecureRandom random;

	private final DbManager dbManager;

	private final int tokenValidInSeconds;

	@Autowired
	public CustomPersistentRememberMeServices(
			DbManager dbManager,
			UserDetailsService userDetailsService,
			@Value("${spring.security.rememberme.cookie.key}") String key,
			@Value("${spring.security.rememberme.cookie.validInDays}") int tokenValidInDays) {
		super(key, userDetailsService);

		this.tokenValidInSeconds = 60 * 60 * 24 * tokenValidInDays;

		this.dbManager = dbManager;
		this.random = new SecureRandom();

		setParameter("remember-me");
	}

	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens,
			HttpServletRequest request, HttpServletResponse response) {

		String series = getPersistentToken(cookieTokens);

		PersistentLogin pl = this.dbManager.get(PersistentLogin.class, series);

		if (pl != null) {

			String token = generateTokenData();
			pl.setLastUsed(LocalDateTime.now());
			pl.setToken(token);
			pl.setIpAddress(request.getRemoteAddr());
			pl.setUserAgent(getUserAgent(request));

			this.dbManager.put(pl, pl.getSeries());

			addCookie(series, token, request, response);

			User user = this.dbManager.get(User.class, pl.getUserId());

			if (user != null) {
				return getUserDetailsService().loadUserByUsername(user.getLoginName());
			}

		}
		return null;
	}

	/**
	 * Creates a new persistent login token with a new series number, stores the data in
	 * the persistent token repository and adds the corresponding cookie to the response.
	 *
	 */
	@Override
	protected void onLoginSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication successfulAuthentication) {

		String loginName = successfulAuthentication.getName();

		this.log.debug("Creating new persistent login for user {}", loginName);

		Optional<User> userOptional = this.dbManager.runInTx(db -> {
			return DbManager.getAll(db, User.class).stream()
					.filter(u -> loginName.equals(u.getLoginName()))
					.filter(u -> !u.isDeleted()).findFirst();
		});

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			PersistentLogin newPersistentLogin = new PersistentLogin();
			newPersistentLogin.setSeries(generateSeriesData());
			newPersistentLogin.setUserId(user.getId());
			newPersistentLogin.setToken(generateTokenData());
			newPersistentLogin.setLastUsed(LocalDateTime.now());
			newPersistentLogin.setIpAddress(request.getRemoteAddr());
			newPersistentLogin.setUserAgent(getUserAgent(request));

			this.dbManager.runInTxWithoutResult(db -> {
				db.getTreeMap(PersistentLogin.class.getName()).put(
						newPersistentLogin.getSeries(), newPersistentLogin);
			});

			addCookie(newPersistentLogin.getSeries(), newPersistentLogin.getToken(),
					request, response);

		}
		else {
			throw new UsernameNotFoundException("User " + loginName
					+ " was not found in the database");

		}

	}

	private static String getUserAgent(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (StringUtils.hasText(userAgent)) {
			return userAgent.substring(0, Math.min(userAgent.length(), 255));
		}
		return null;
	}

	/**
	 * When logout occurs, only invalidate the current token, and not all user sessions.
	 * <p/>
	 * The standard Spring Security implementations are too basic: they invalidate all
	 * tokens for the current user, so when he logs out from one browser, all his other
	 * sessions are destroyed.
	 */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {

		String rememberMeCookie = extractRememberMeCookie(request);
		if (rememberMeCookie != null && rememberMeCookie.length() != 0) {
			try {
				String[] cookieTokens = decodeCookie(rememberMeCookie);
				removePersistentLogin(getPersistentToken(cookieTokens));
			}
			catch (InvalidCookieException ice) {
				this.log.info("Invalid cookie, no persistent token could be deleted");
			}
			catch (RememberMeAuthenticationException rmae) {
				this.log.debug("No persistent token found, so no token could be deleted");
			}
		}

		super.logout(request, response, authentication);
	}

	private void removePersistentLogin(String series) {
		this.dbManager.remove(PersistentLogin.class, series);
	}

	/**
	 * Validate the token and return it.
	 */
	private String getPersistentToken(String[] cookieTokens) {

		if (cookieTokens.length != 2) {
			throw new InvalidCookieException("Cookie token did not contain " + 2
					+ " tokens, but contained '" + Arrays.toString(cookieTokens) + "'");
		}

		final String presentedSeries = cookieTokens[0];
		final String presentedToken = cookieTokens[1];

		PersistentLogin persistentLogin = this.dbManager.get(PersistentLogin.class,
				presentedSeries);

		if (persistentLogin == null) {
			// No series match, so we can't authenticate using this cookie
			throw new RememberMeAuthenticationException(
					"No persistent token found for series id: " + presentedSeries);
		}

		// We have a match for this user/series combination
		if (!presentedToken.equals(persistentLogin.getToken())) {
			// Presented token doesn't match stored token. Delete persistentLogin
			removePersistentLogin(persistentLogin.getSeries());

			throw new CookieTheftException(
					this.messages
							.getMessage(
									"PersistentTokenBasedRememberMeServices.cookieStolen",
									"Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack."));
		}

		if (persistentLogin.getLastUsed().plusSeconds(this.tokenValidInSeconds)
				.isBefore(LocalDateTime.now())) {
			removePersistentLogin(persistentLogin.getSeries());
			throw new RememberMeAuthenticationException("Remember-me login has expired");
		}

		return persistentLogin.getSeries();
	}

	private String generateSeriesData() {
		byte[] newSeries = new byte[16];
		this.random.nextBytes(newSeries);
		return Base64.getEncoder().encodeToString(newSeries);
	}

	private String generateTokenData() {
		byte[] newToken = new byte[16];
		this.random.nextBytes(newToken);
		return Base64.getEncoder().encodeToString(newToken);
	}

	private void addCookie(String series, String token, HttpServletRequest request,
			HttpServletResponse response) {
		setCookie(new String[] { series, token }, this.tokenValidInSeconds, request,
				response);
	}

}
