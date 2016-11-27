package ch.rasc.e4desk.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.POLL;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.e4desk.entity.AccessLog;
import ch.rasc.e4desk.entity.User;
import ch.rasc.e4desk.security.JpaUserDetails;
import ch.rasc.e4desk.util.Util;

@Service
public class InfrastructureService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private MailService mailService;

	@Autowired
	private Environment environment;

	@ExtDirectMethod(value = POLL, event = "heartbeat")
	@PreAuthorize("isAuthenticated()")
	public void heartbeat() {
		// nothing here
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional
	public boolean switchUser(Long userId) {

		User switchToUser = this.entityManager.find(User.class, userId);
		if (switchToUser != null) {
			Util.signin(switchToUser);
			return true;
		}

		return false;
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public String getLoggedOnUser(HttpSession session, HttpServletRequest request) {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof JpaUserDetails) {
			JpaUserDetails userDetail = (JpaUserDetails) principal;

			if (session != null) {
				AccessLog accessLog = new AccessLog();
				accessLog.setUserName(userDetail.getUsername());
				accessLog.setSessionId(session.getId());
				accessLog.setLogIn(DateTime.now());
				accessLog.setUserAgent(request.getHeader("User-Agent"));

				this.entityManager.persist(accessLog);
			}

			return userDetail.getFullName();

		}
		return principal.toString();
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public UserSettings getUserSettings() {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof JpaUserDetails) {
			JpaUserDetails userDetail = (JpaUserDetails) principal;
			User user = this.entityManager.find(User.class, userDetail.getUserDbId());
			if (user.getSettings() != null) {
				return deserialUserSettings(user.getSettings());
			}
		}
		return null;
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public void saveUserSettings(String wallpaper, Integer width, Integer height,
			String picturePos, String backgroundColor) throws IOException {

		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof JpaUserDetails) {
			JpaUserDetails userDetail = (JpaUserDetails) principal;
			User user = this.entityManager.find(User.class, userDetail.getUserDbId());
			UserSettings us = new UserSettings(wallpaper, width, height, picturePos,
					backgroundColor);
			user.setSettings(serializeUserSettings(us));
		}
	}

	private static String serializeUserSettings(UserSettings us) throws IOException {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			oos.writeObject(us);
			oos.flush();
			return DatatypeConverter.printBase64Binary(bos.toByteArray());
		}
	}

	private static UserSettings deserialUserSettings(String s) {

		if (s != null) {
			byte[] b = DatatypeConverter.parseBase64Binary(s);
			try (ByteArrayInputStream bis = new ByteArrayInputStream(b);
					ObjectInputStream ois = new ObjectInputStream(bis)) {
				return (UserSettings) ois.readObject();
			}
			catch (IOException | ClassNotFoundException e) {
				// do nothing here
			}
		}

		return null;
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	public void sendFeedback(String feedbackText) throws MessagingException {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof JpaUserDetails) {
			String username = ((JpaUserDetails) principal).getUsername();
			String to;
			if (this.environment.acceptsProfiles("development")) {
				to = "test@test.com";
			}
			else {
				to = "someproductionemail@test.com";
			}
			this.mailService.sendHtmlMessage(username, to, "Feedback from e4ds-desktop",
					feedbackText);
		}
	}

}
