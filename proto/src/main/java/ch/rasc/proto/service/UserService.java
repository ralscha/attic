package ch.rasc.proto.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.edsutil.ValidationUtil;
import ch.rasc.edsutil.bean.ValidationMessages;
import ch.rasc.edsutil.bean.ValidationMessagesResult;
import ch.rasc.proto.config.DbManager;
import ch.rasc.proto.config.security.AppUserDetails;
import ch.rasc.proto.dto.UserSettings;
import ch.rasc.proto.entity.Role;
import ch.rasc.proto.entity.User;

@Service
@Lazy
@PreAuthorize("hasRole('ADMIN')")
public class UserService {

	private final MessageSource messageSource;

	private final PasswordEncoder passwordEncoder;

	private final Validator validator;

	private final DbManager dbManager;

	private final MailService mailService;

	@Autowired
	public UserService(DbManager dbManager, Validator validator,
			PasswordEncoder passwordEncoder, MessageSource messageSource,
			MailService mailService) {
		this.dbManager = dbManager;
		this.messageSource = messageSource;
		this.validator = validator;
		this.mailService = mailService;
		this.passwordEncoder = passwordEncoder;
	}

	@ExtDirectMethod(STORE_READ)
	public ExtDirectStoreResult<User> read() throws InterruptedException {
		return this.dbManager.runInTx(db -> {
			Collection<User> users = DbManager.getAll(db, User.class);
			return new ExtDirectStoreResult<>(users.size(), users);
		});
	}

	@ExtDirectMethod(STORE_MODIFY)
	public ExtDirectStoreResult<User> destroy(User destroyUser) {
		ExtDirectStoreResult<User> result = new ExtDirectStoreResult<>();
		if (!isLastAdmin(destroyUser.getId())) {
			this.dbManager.remove(destroyUser);
			result.setSuccess(Boolean.TRUE);
		}
		else {
			result.setSuccess(Boolean.FALSE);
		}
		return result;
	}

	@ExtDirectMethod(STORE_MODIFY)
	public ValidationMessagesResult<User> update(User updatedEntity, Locale locale) {
		preModify(updatedEntity);
		List<ValidationMessages> violations = validateEntity(updatedEntity, locale);
		if (violations.isEmpty()) {

			this.dbManager.put(updatedEntity);
			if (updatedEntity.isPasswordReset()) {
				sendPassordResetEmail(updatedEntity);
			}

			return new ValidationMessagesResult<>(updatedEntity);
		}

		ValidationMessagesResult<User> result = new ValidationMessagesResult<>(
				updatedEntity);
		result.setValidations(violations);
		return result;
	}

	private void sendPassordResetEmail(User user) {
		String token = UUID.randomUUID().toString();
		this.mailService.sendPasswortResetEmail(user, token);

		user.setPasswordResetTokenValidUntil(LocalDateTime.now().plusHours(4));
		user.setPasswordResetToken(token);

		this.dbManager.put(user);
	}

	private List<ValidationMessages> validateEntity(User user, Locale locale) {
		List<ValidationMessages> validations = ValidationUtil.validateEntity(
				this.validator, user);

		if (!loginNameUnique(user.getId(), user.getLoginName())) {
			ValidationMessages validationError = new ValidationMessages();
			validationError.setField("loginName");
			validationError.setMessage(this.messageSource.getMessage(
					"user_loginnametaken", null, locale));
			validations.add(validationError);
		}

		return validations;
	}

	private void preModify(User entity) {
		if (entity.getId() != null && entity.getId() > 0) {
			User user = this.dbManager.get(User.class, entity.getId());
			entity.setPasswordHash(user.getPasswordHash());
		}
	}

	private boolean isLastAdmin(Long excludeUserId) {
		Optional<User> user = this.dbManager.getAll(User.class).stream()
				.filter(u -> !u.getId().equals(excludeUserId))
				.filter(u -> !u.isDeleted()).filter(u -> u.getRole() != null)
				.filter(u -> u.getRole().equals(Role.ADMIN.name()))
				.filter(u -> u.getRole().endsWith("," + Role.ADMIN.name()))
				.filter(u -> u.getRole().contains("," + Role.ADMIN.name() + ","))
				.filter(u -> u.getRole().startsWith(Role.ADMIN.name() + ",")).findAny();

		return !user.isPresent();
	}

	private boolean loginNameUnique(Long userId, String loginName) {
		// Check uniqueness of loginName
		if (StringUtils.hasText(loginName)) {

			String lowerCaseLoginName = loginName.toLowerCase();

			Optional<User> user = this.dbManager
					.getAll(User.class)
					.stream()
					.filter(u -> userId == null || !u.getId().equals(userId))
					.filter(u -> !u.isDeleted())
					.filter(u -> u.getLoginName().toLowerCase()
							.equals(lowerCaseLoginName)).findAny();

			return !user.isPresent();
		}

		return true;
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	public List<Map<String, String>> readRoles() {
		return Arrays.stream(Role.values())
				.map(r -> Collections.singletonMap("name", r.name()))
				.collect(Collectors.toList());
	}

	@ExtDirectMethod
	public boolean unlock(Long userId) {
		return this.dbManager.runInTx(db -> {
			User user = DbManager.get(db, User.class, userId);
			if (user != null) {
				user.setLockedOutUntil(null);
				user.setFailedLogins(null);
				DbManager.put(db, user);
				return true;
			}
			return false;
		});
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	public UserSettings readSettings(
			@AuthenticationPrincipal AppUserDetails jpaUserDetails) {
		User user = this.dbManager.get(User.class, jpaUserDetails.getUserDbId());
		return new UserSettings(user);
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	public List<ValidationMessages> updateSettings(UserSettings modifiedUserSettings,
			@AuthenticationPrincipal AppUserDetails jpaUserDetails, Locale locale) {

		List<ValidationMessages> validations = ValidationUtil.validateEntity(
				this.validator, modifiedUserSettings);

		User user = this.dbManager.get(User.class, jpaUserDetails.getUserDbId());

		if (StringUtils.hasText(modifiedUserSettings.getNewPassword())) {
			if (this.passwordEncoder.matches(modifiedUserSettings.getCurrentPassword(),
					user.getPasswordHash())) {
				if (modifiedUserSettings.getNewPassword().equals(
						modifiedUserSettings.getNewPasswordRetype())) {
					user.setPasswordHash(this.passwordEncoder.encode(modifiedUserSettings
							.getNewPassword()));
				}
				else {
					for (String field : new String[] { "newPassword", "newPasswordRetype" }) {
						ValidationMessages error = new ValidationMessages();
						error.setField(field);
						error.setMessage(this.messageSource.getMessage(
								"user_pwdonotmatch", null, locale));
						validations.add(error);
					}
				}
			}
			else {
				ValidationMessages error = new ValidationMessages();
				error.setField("currentPassword");
				error.setMessage(this.messageSource.getMessage("user_wrongpassword",
						null, locale));
				validations.add(error);
			}
		}

		if (validations.isEmpty()) {
			user.setLastName(modifiedUserSettings.getLastName());
			user.setFirstName(modifiedUserSettings.getFirstName());
			user.setEmail(modifiedUserSettings.getEmail());
			user.setLocale(modifiedUserSettings.getLocale());
			this.dbManager.put(user);
		}

		return validations;
	}

}
