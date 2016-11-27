package ch.rasc.eds.starter.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.eds.starter.config.security.JpaUserDetails;
import ch.rasc.eds.starter.dto.UserSettings;
import ch.rasc.eds.starter.entity.QAccessLog;
import ch.rasc.eds.starter.entity.QUser;
import ch.rasc.eds.starter.entity.Role;
import ch.rasc.eds.starter.entity.User;
import ch.rasc.edsutil.JPAQueryFactory;
import ch.rasc.edsutil.QuerydslUtil;
import ch.rasc.edsutil.ValidationUtil;
import ch.rasc.edsutil.bean.ValidationMessages;
import ch.rasc.edsutil.bean.ValidationMessagesResult;

@Service
@Lazy
@PreAuthorize("hasRole('ADMIN')")
public class UserService {

	private final MessageSource messageSource;

	private final PasswordEncoder passwordEncoder;

	private final Validator validator;

	private final JPAQueryFactory jpaQueryFactory;

	private final MailService mailService;

	@Autowired
	public UserService(JPAQueryFactory jpaQueryFactory, Validator validator,
			PasswordEncoder passwordEncoder, MessageSource messageSource,
			MailService mailService) {
		this.jpaQueryFactory = jpaQueryFactory;
		this.messageSource = messageSource;
		this.validator = validator;
		this.mailService = mailService;
		this.passwordEncoder = passwordEncoder;
	}

	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<User> read(ExtDirectStoreReadRequest request) {

		JPQLQuery<User> query = this.jpaQueryFactory.selectFrom(QUser.user);
		if (!request.getFilters().isEmpty()) {
			StringFilter filter = (StringFilter) request.getFilters().iterator().next();

			BooleanBuilder bb = new BooleanBuilder();
			bb.or(QUser.user.loginName.containsIgnoreCase(filter.getValue()));
			bb.or(QUser.user.lastName.containsIgnoreCase(filter.getValue()));
			bb.or(QUser.user.firstName.containsIgnoreCase(filter.getValue()));
			bb.or(QUser.user.email.containsIgnoreCase(filter.getValue()));

			query.where(bb);
		}
		query.where(QUser.user.deleted.isFalse());

		QuerydslUtil.addPagingAndSorting(query, request, User.class, QUser.user);
		QueryResults<User> searchResult = query.fetchResults();

		for (User u : searchResult.getResults()) {
			ZonedDateTime ts = this.jpaQueryFactory
					.select(QAccessLog.accessLog.loginTimestamp)
					.from(QAccessLog.accessLog)
					.where(QAccessLog.accessLog.loginName.eq(u.getLoginName()))
					.orderBy(QAccessLog.accessLog.loginTimestamp.desc()).fetchFirst();
			u.setLastLogin(ts);
		}

		return new ExtDirectStoreResult<>(searchResult.getTotal(),
				searchResult.getResults());
	}

	@ExtDirectMethod(STORE_MODIFY)
	@Transactional
	public ExtDirectStoreResult<User> destroy(User destroyUser) {
		ExtDirectStoreResult<User> result = new ExtDirectStoreResult<>();
		if (!isLastAdmin(destroyUser.getId())) {
			User user = this.jpaQueryFactory.getEntityManager().find(User.class,
					destroyUser.getId());
			user.setDeleted(true);
			result.setSuccess(Boolean.TRUE);
		}
		else {
			result.setSuccess(Boolean.FALSE);
		}
		return result;
	}

	@ExtDirectMethod(STORE_MODIFY)
	@Transactional
	public ValidationMessagesResult<User> update(User updatedEntity, Locale locale) {
		preModify(updatedEntity);
		List<ValidationMessages> violations = validateEntity(updatedEntity, locale);
		if (violations.isEmpty()) {
			User merged = this.jpaQueryFactory.getEntityManager().merge(updatedEntity);
			if (updatedEntity.isPasswordReset()) {
				sendPassordResetEmail(merged);
			}

			return new ValidationMessagesResult<>(merged);
		}

		ValidationMessagesResult<User> result = new ValidationMessagesResult<>(
				updatedEntity);
		result.setValidations(violations);
		return result;
	}

	private void sendPassordResetEmail(User user) {
		String token = UUID.randomUUID().toString();
		this.mailService.sendPasswortResetEmail(user, token);

		user.setPasswordResetTokenValidUntil(
				ZonedDateTime.now(ZoneOffset.UTC).plusHours(4));
		user.setPasswordResetToken(token);
		this.jpaQueryFactory.getEntityManager().merge(user);
	}

	private List<ValidationMessages> validateEntity(User user, Locale locale) {
		List<ValidationMessages> validations = ValidationUtil
				.validateEntity(this.validator, user);

		if (!loginNameUnique(user.getId(), user.getLoginName())) {
			ValidationMessages validationError = new ValidationMessages();
			validationError.setField("loginName");
			validationError.setMessage(
					this.messageSource.getMessage("user_loginnametaken", null, locale));
			validations.add(validationError);
		}

		return validations;
	}

	private void preModify(User entity) {
		if (entity.getId() != null && entity.getId() > 0) {
			String dbPassword = this.jpaQueryFactory.select(QUser.user.passwordHash)
					.from(QUser.user).where(QUser.user.id.eq(entity.getId()))
					.fetchFirst();
			entity.setPasswordHash(dbPassword);
		}

	}

	private boolean isLastAdmin(Long id) {
		JPAQuery<Integer> query = this.jpaQueryFactory.select(Expressions.ONE)
				.from(QUser.user);
		BooleanBuilder bb = new BooleanBuilder();
		bb.or(QUser.user.role.eq(Role.ADMIN.name()));
		bb.or(QUser.user.role.endsWith("," + Role.ADMIN.name()));
		bb.or(QUser.user.role.contains("," + Role.ADMIN.name() + ","));
		bb.or(QUser.user.role.startsWith(Role.ADMIN.name() + ","));

		query.where(QUser.user.id.ne(id).and(QUser.user.deleted.isFalse()).and(bb));
		return query.fetchFirst() == null;
	}

	private boolean loginNameUnique(Long userId, String loginName) {
		// Check uniqueness of loginName
		if (StringUtils.hasText(loginName)) {
			BooleanBuilder bb = new BooleanBuilder(
					QUser.user.loginName.equalsIgnoreCase(loginName));
			if (userId != null) {
				bb.and(QUser.user.id.ne(userId));
			}
			bb.and(QUser.user.deleted.isFalse());
			return this.jpaQueryFactory.select(Expressions.ONE).from(QUser.user).where(bb)
					.fetchFirst() == null;
		}

		return false;
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	public List<Map<String, String>> readRoles() {
		return Arrays.stream(Role.values())
				.map(r -> Collections.singletonMap("name", r.name()))
				.collect(Collectors.toList());
	}

	@ExtDirectMethod
	@Transactional
	public boolean unlock(Long userId) {
		User user = this.jpaQueryFactory.getEntityManager().find(User.class, userId);
		if (user != null) {
			user.setLockedOutUntil(null);
			user.setFailedLogins(null);
			this.jpaQueryFactory.getEntityManager().persist(user);
			return true;
		}
		return false;
	}

	@ExtDirectMethod
	@Transactional(readOnly = true)
	@PreAuthorize("isAuthenticated()")
	public UserSettings readSettings(
			@AuthenticationPrincipal JpaUserDetails jpaUserDetails) {
		return new UserSettings(this.jpaQueryFactory.getEntityManager().find(User.class,
				jpaUserDetails.getUserDbId()));
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public List<ValidationMessages> updateSettings(UserSettings modifiedUserSettings,
			@AuthenticationPrincipal JpaUserDetails jpaUserDetails, Locale locale) {

		List<ValidationMessages> validations = ValidationUtil
				.validateEntity(this.validator, modifiedUserSettings);
		User user = this.jpaQueryFactory.getEntityManager().find(User.class,
				jpaUserDetails.getUserDbId());

		if (StringUtils.hasText(modifiedUserSettings.getNewPassword())) {
			if (this.passwordEncoder.matches(modifiedUserSettings.getCurrentPassword(),
					user.getPasswordHash())) {
				if (modifiedUserSettings.getNewPassword()
						.equals(modifiedUserSettings.getNewPasswordRetype())) {
					user.setPasswordHash(this.passwordEncoder
							.encode(modifiedUserSettings.getNewPassword()));
				}
				else {
					for (String field : new String[] { "newPassword",
							"newPasswordRetype" }) {
						ValidationMessages error = new ValidationMessages();
						error.setField(field);
						error.setMessage(this.messageSource
								.getMessage("user_pwdonotmatch", null, locale));
						validations.add(error);
					}
				}
			}
			else {
				ValidationMessages error = new ValidationMessages();
				error.setField("currentPassword");
				error.setMessage(this.messageSource.getMessage("user_wrongpassword", null,
						locale));
				validations.add(error);
			}
		}

		if (validations.isEmpty()) {
			user.setLastName(modifiedUserSettings.getLastName());
			user.setFirstName(modifiedUserSettings.getFirstName());
			user.setEmail(modifiedUserSettings.getEmail());
			user.setLocale(modifiedUserSettings.getLocale());
		}

		return validations;
	}

}
