package ch.rasc.e4desk.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.FORM_POST;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Splitter;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.e4desk.entity.QRole;
import ch.rasc.e4desk.entity.QUser;
import ch.rasc.e4desk.entity.Role;
import ch.rasc.e4desk.entity.User;
import ch.rasc.e4desk.security.JpaUserDetails;
import ch.rasc.edsutil.QueryUtil;

@Service
@Lazy
public class UserService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSource messageSource;

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<User> read(ExtDirectStoreReadRequest request,
			Locale locale) {

		JPQLQuery query = new JPAQuery(this.entityManager).from(QUser.user);
		if (!request.getFilters().isEmpty()) {
			StringFilter filter = (StringFilter) request.getFilters().iterator().next();

			String likeValue = "%" + filter.getValue().toLowerCase() + "%";

			BooleanBuilder bb = new BooleanBuilder();

			bb.or(QUser.user.name.lower().like(likeValue));
			bb.or(QUser.user.firstName.lower().like(likeValue));
			bb.or(QUser.user.email.lower().like(likeValue));

			query.where(bb);
		}

		QueryUtil.addPagingAndSorting(query, request, User.class, QUser.user);
		SearchResults<User> searchResult = query.listResults(QUser.user);

		PeriodFormatter periodFormatter = new PeriodFormatterBuilder().appendDays()
				.appendSuffix(" " + this.messageSource.getMessage("day", null, locale),
						" " + this.messageSource.getMessage("days", null, locale))
				.appendSeparator(
						" " + this.messageSource.getMessage("and", null, locale) + " ")
				.appendHours()
				.appendSuffix(" " + this.messageSource.getMessage("hour", null, locale),
						" " + this.messageSource.getMessage("hours", null, locale))
				.toFormatter();

		for (User user : searchResult.getResults()) {
			if (user.getLastLogin() != null) {
				Period period = new Duration(user.getLastLogin(), null).toPeriod()
						.normalizedStandard(PeriodType.dayTime());
				String output = periodFormatter.print(period);
				if (StringUtils.hasText(output) && output.length() > 4) {
					user.setLastLoginDescription(output);
				}
				user.setLastLoginDescription(this.messageSource
						.getMessage("user_lastloginlessthanahour", null, locale));
			}
		}

		return new ExtDirectStoreResult<>(searchResult.getTotal(),
				searchResult.getResults());
	}

	@ExtDirectMethod(STORE_MODIFY)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional
	public void destroy(User destroyUser) {
		if (!isLastAdmin(destroyUser)) {
			this.entityManager
					.remove(this.entityManager.find(User.class, destroyUser.getId()));
		}
	}

	@ExtDirectMethod(FORM_POST)
	@Transactional
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ExtDirectFormPostResult userFormPost(
			@RequestParam(value = "id", required = false) final Long userId,
			@RequestParam(required = false) final String roleIds,
			@Valid final User modifiedUser, final BindingResult bindingResult,
			Locale locale) {

		// Check uniqueness of email
		if (!bindingResult.hasErrors()) {

			BooleanBuilder bb = new BooleanBuilder(
					QUser.user.email.equalsIgnoreCase(modifiedUser.getEmail()));
			if (userId != null) {
				bb.and(QUser.user.id.ne(userId));
			}

			if (new JPAQuery(this.entityManager).from(QUser.user).where(bb).exists()) {
				bindingResult.rejectValue("email", null,
						this.messageSource.getMessage("user_emailtaken", null, locale));
			}
		}

		if (!bindingResult.hasErrors()) {

			if (StringUtils.hasText(modifiedUser.getPasswordHash())) {
				modifiedUser.setPasswordHash(
						this.passwordEncoder.encode(modifiedUser.getPasswordHash()));
			}

			Set<Role> roles = new HashSet<>();
			if (StringUtils.hasText(roleIds)) {
				Iterable<String> roleIdsIt = Splitter.on(",").split(roleIds);
				for (String roleId : roleIdsIt) {
					roles.add(this.entityManager.find(Role.class, Long.valueOf(roleId)));
				}
			}

			if (userId != null) {
				User dbUser = this.entityManager.find(User.class, userId);
				if (dbUser != null) {
					dbUser.getRoles().clear();
					dbUser.getRoles().addAll(roles);

					dbUser.setEnabled(modifiedUser.isEnabled());
					dbUser.setName(modifiedUser.getName());
					dbUser.setFirstName(modifiedUser.getFirstName());
					dbUser.setEmail(modifiedUser.getEmail());
					dbUser.setLocale(modifiedUser.getLocale());

					if (StringUtils.hasText(modifiedUser.getPasswordHash())) {
						dbUser.setPasswordHash(modifiedUser.getPasswordHash());
					}
				}
			}
			else {
				modifiedUser.setRoles(roles);
				this.entityManager.persist(modifiedUser);
			}

		}

		return new ExtDirectFormPostResult(bindingResult);
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public User getLoggedOnUserObject() {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof JpaUserDetails) {
			return this.entityManager.find(User.class,
					((JpaUserDetails) principal).getUserDbId());
		}
		return null;
	}

	private boolean isLastAdmin(User user) {
		Role role = new JPAQuery(this.entityManager).from(QRole.role)
				.where(QRole.role.name.eq("ROLE_ADMIN")).singleResult(QRole.role);
		JPQLQuery query = new JPAQuery(this.entityManager).from(QUser.user);
		query.where(QUser.user.ne(user).and(QUser.user.roles.contains(role)));
		return query.notExists();
	}

	@ExtDirectMethod(ExtDirectMethodType.FORM_LOAD)
	@Transactional(readOnly = true)
	@PreAuthorize("isAuthenticated()")
	public User userFormSettingsLoad() {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof JpaUserDetails) {
			JpaUserDetails userDetail = (JpaUserDetails) principal;
			return this.entityManager.find(User.class, userDetail.getUserDbId());
		}
		return null;
	}

	@ExtDirectMethod(FORM_POST)
	@Transactional
	@PreAuthorize("isAuthenticated()")
	public ExtDirectFormPostResult userFormSettingsPost(@Valid final User modifiedUser,
			final BindingResult bindingResult, Locale locale) {

		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (!bindingResult.hasErrors()) {
			String oldEmail = ((JpaUserDetails) principal).getUsername();
			if (!oldEmail.equalsIgnoreCase(modifiedUser.getEmail())) {
				BooleanBuilder bb = new BooleanBuilder(
						QUser.user.email.equalsIgnoreCase(modifiedUser.getEmail()));
				bb.and(QUser.user.email.ne(oldEmail));
				if (new JPAQuery(this.entityManager).from(QUser.user).where(bb)
						.exists()) {
					bindingResult.rejectValue("email", null, this.messageSource
							.getMessage("user_emailtaken", null, locale));
					return new ExtDirectFormPostResult(bindingResult);
				}
			}
		}

		if (principal instanceof JpaUserDetails) {
			JpaUserDetails userDetail = (JpaUserDetails) principal;
			User user = this.entityManager.find(User.class, userDetail.getUserDbId());
			user.setEmail(modifiedUser.getEmail());
			user.setFirstName(modifiedUser.getFirstName());
			user.setName(modifiedUser.getName());
			user.setLocale(modifiedUser.getLocale());

			if (StringUtils.hasText(modifiedUser.getPasswordHash())) {
				user.setPasswordHash(
						this.passwordEncoder.encode(modifiedUser.getPasswordHash()));
			}

		}
		return new ExtDirectFormPostResult();
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	public List<Role> readRoles() {
		return new JPAQuery(this.entityManager).from(QRole.role)
				.orderBy(QRole.role.name.asc()).list(QRole.role);
	}

}
