package ch.rasc.changelog.service;

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
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.changelog.entity.QRole;
import ch.rasc.changelog.entity.QUser;
import ch.rasc.changelog.entity.Role;
import ch.rasc.changelog.entity.User;
import ch.rasc.changelog.security.JpaUserDetails;
import ch.rasc.edsutil.QueryUtil;

@Service
@Lazy
public class UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSource messageSource;

	@PersistenceContext
	private EntityManager entityManager;

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<User> read(ExtDirectStoreReadRequest request) {

		JPQLQuery query = new JPAQuery(this.entityManager).from(QUser.user);
		if (!request.getFilters().isEmpty()) {
			StringFilter filter = (StringFilter) request.getFilters().iterator().next();

			BooleanBuilder bb = new BooleanBuilder();
			bb.or(QUser.user.userName.contains(filter.getValue()));
			bb.or(QUser.user.name.contains(filter.getValue()));
			bb.or(QUser.user.firstName.contains(filter.getValue()));
			bb.or(QUser.user.email.contains(filter.getValue()));

			query.where(bb);
		}

		QueryUtil.addPagingAndSorting(query, request, User.class, QUser.user);
		SearchResults<User> searchResult = query.listResults(QUser.user);

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
	@PreAuthorize("isAuthenticated()")
	public ExtDirectFormPostResult userFormPost(Locale locale,
			@RequestParam(required = false, defaultValue = "false") final boolean options,
			@RequestParam(value = "id", required = false) final Long userId,
			@RequestParam(required = false) final String roleIds,
			@Valid final User modifiedUser, final BindingResult bindingResult) {

		// Check uniqueness of userName and email
		if (!bindingResult.hasErrors()) {
			if (!options) {
				BooleanBuilder bb = new BooleanBuilder(
						QUser.user.userName.equalsIgnoreCase(modifiedUser.getUserName()));
				if (userId != null) {
					bb.and(QUser.user.id.ne(userId));
				}
				if (new JPAQuery(this.entityManager).from(QUser.user).where(bb)
						.exists()) {
					bindingResult.rejectValue("userName", null, this.messageSource
							.getMessage("user_usernametaken", null, locale));
				}
			}

			BooleanBuilder bb = new BooleanBuilder(
					QUser.user.email.equalsIgnoreCase(modifiedUser.getEmail()));
			if (userId != null && !options) {
				bb.and(QUser.user.id.ne(userId));
			}
			else if (options) {
				Object principal = SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
				if (principal instanceof JpaUserDetails) {
					bb.and(QUser.user.id.ne(((JpaUserDetails) principal).getUserDbId()));
				}
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

			if (!options) {
				Set<Role> roles = new HashSet<>();
				if (StringUtils.hasText(roleIds)) {
					Iterable<String> roleIdsIt = Splitter.on(",").split(roleIds);
					for (String roleId : roleIdsIt) {
						roles.add(this.entityManager.find(Role.class,
								Long.valueOf(roleId)));
					}
				}

				if (userId != null) {
					User dbUser = this.entityManager.find(User.class, userId);
					if (dbUser != null) {
						dbUser.getRoles().clear();
						dbUser.getRoles().addAll(roles);

						dbUser.setUserName(modifiedUser.getUserName());
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
			else {
				Object principal = SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
				if (principal instanceof JpaUserDetails) {
					User dbUser = this.entityManager.find(User.class,
							((JpaUserDetails) principal).getUserDbId());
					if (dbUser != null) {
						dbUser.setName(modifiedUser.getName());
						dbUser.setFirstName(modifiedUser.getFirstName());
						dbUser.setEmail(modifiedUser.getEmail());
						dbUser.setLocale(modifiedUser.getLocale());
						if (StringUtils.hasText(modifiedUser.getPasswordHash())) {
							if (this.passwordEncoder.matches(
									modifiedUser.getOldPassword(),
									dbUser.getPasswordHash())) {
								dbUser.setPasswordHash(modifiedUser.getPasswordHash());
							}
							else {
								bindingResult.rejectValue("oldPassword", null,
										this.messageSource.getMessage(
												"user_wrongpassword", null, locale));
							}
						}
					}
				}
			}
		}

		return new ExtDirectFormPostResult(bindingResult);
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public User getLoggedOnUser() {
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

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public List<Role> readRoles() {
		return new JPAQuery(this.entityManager).from(QRole.role)
				.orderBy(QRole.role.name.asc()).list(QRole.role);
	}

}
