#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ${package}.entity.QUser;
import ${package}.entity.Role;
import ${package}.entity.User;
import ${package}.security.JpaUserDetails;
import ch.rasc.edsutil.BaseCRUDService;
import ch.rasc.edsutil.QueryUtil;
import ch.rasc.edsutil.bean.ExtDirectStoreValidationResult;
import ch.rasc.edsutil.bean.ValidationError;

import com.google.common.collect.ImmutableMap;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
@Lazy
@PreAuthorize("hasRole('ADMIN')")
public class UserService extends BaseCRUDService<User> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSource messageSource;

	@Override
	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<User> read(ExtDirectStoreReadRequest request) {

		JPQLQuery query = new JPAQuery(entityManager).from(QUser.user);
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

	@ExtDirectMethod
	@Transactional
	@PreAuthorize("isAuthenticated()")
	public ExtDirectStoreValidationResult<User> updateSettings(User modifiedUser,
			Locale locale) {

		List<ValidationError> validations = new ArrayList<>();
		User dbUser = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof JpaUserDetails) {
			dbUser = entityManager.find(User.class,
					((JpaUserDetails) principal).getUserDbId());
			if (dbUser != null) {
				dbUser.setName(modifiedUser.getName());
				dbUser.setFirstName(modifiedUser.getFirstName());
				dbUser.setEmail(modifiedUser.getEmail());
				dbUser.setLocale(modifiedUser.getLocale());
				if (StringUtils.hasText(modifiedUser.getPasswordNew())) {
					if (passwordEncoder.matches(modifiedUser.getOldPassword(),
							dbUser.getPasswordHash())) {
						if (modifiedUser.getPasswordNew().equals(
								modifiedUser.getPasswordNewConfirm())) {
							dbUser.setPasswordHash(passwordEncoder.encode(modifiedUser
									.getPasswordNew()));
						}
						else {
							ValidationError error = new ValidationError();
							error.setField("passwordNew");
							error.setMessage(messageSource.getMessage(
									"user_passworddonotmatch", null, locale));
							validations.add(error);
						}
					}
					else {
						ValidationError error = new ValidationError();
						error.setField("oldPassword");
						error.setMessage(messageSource.getMessage("user_wrongpassword",
								null, locale));
						validations.add(error);
					}
				}
			}
		}

		ExtDirectStoreValidationResult<User> result = new ExtDirectStoreValidationResult<>(
				dbUser);
		result.setValidations(validations);
		return result;
	}

	@Override
	protected List<ValidationError> validateEntity(User entity) {
		List<ValidationError> validations = super.validateEntity(entity);

		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Locale locale = Locale.ENGLISH;
		if (principal instanceof JpaUserDetails) {
			locale = ((JpaUserDetails) principal).getLocale();
		}

		if (entity.getPasswordNew() != null
				&& !entity.getPasswordNew().equals(entity.getPasswordNewConfirm())) {
			ValidationError validationError = new ValidationError();
			validationError.setField("passwordNew");
			validationError.setMessage(messageSource.getMessage(
					"user_passworddonotmatch", null, locale));
			validations.add(validationError);
		}

		// Check uniqueness of userName and email
		if (StringUtils.hasText(entity.getUserName())) {
			BooleanBuilder bb = new BooleanBuilder(
					QUser.user.userName.equalsIgnoreCase(entity.getUserName()));
			if (entity.getId() != null) {
				bb.and(QUser.user.id.ne(entity.getId()));
			}
			if (new JPAQuery(entityManager).from(QUser.user).where(bb).exists()) {
				ValidationError validationError = new ValidationError();
				validationError.setField("userName");
				validationError.setMessage(messageSource.getMessage("user_usernametaken",
						null, locale));
				validations.add(validationError);
			}
		}

		if (StringUtils.hasText(entity.getEmail())) {
			BooleanBuilder bb = new BooleanBuilder(
					QUser.user.email.equalsIgnoreCase(entity.getEmail()));
			if (entity.getId() != null) {
				bb.and(QUser.user.id.ne(entity.getId()));
			}
			if (new JPAQuery(entityManager).from(QUser.user).where(bb).exists()) {
				ValidationError validationError = new ValidationError();
				validationError.setField("email");
				validationError.setMessage(messageSource.getMessage("user_emailtaken",
						null, locale));
				validations.add(validationError);
			}
		}

		return validations;
	}

	@Override
	protected void preModify(User entity) {
		super.preModify(entity);

		if (StringUtils.hasText(entity.getPasswordNew())) {
			entity.setPasswordHash(passwordEncoder.encode(entity.getPasswordNew()));
		}
		else {
			if (entity.getId() != null) {
				String dbPassword = new JPAQuery(entityManager).from(QUser.user)
						.where(QUser.user.id.eq(entity.getId()))
						.singleResult(QUser.user.passwordHash);
				entity.setPasswordHash(dbPassword);
			}
		}
	}

	@Override
	@Transactional
	public ExtDirectStoreResult<User> destroy(Long id) {
		if (!isLastAdmin(id)) {
			return super.destroy(id);
		}

		ExtDirectStoreResult<User> result = new ExtDirectStoreResult<>();
		result.setSuccess(false);
		return result;
	}

	private boolean isLastAdmin(Long id) {
		JPQLQuery query = new JPAQuery(entityManager).from(QUser.user);
		BooleanBuilder bb = new BooleanBuilder();
		bb.or(QUser.user.role.eq(Role.ADMIN.name()));
		bb.or(QUser.user.role.endsWith("," + Role.ADMIN.name()));
		bb.or(QUser.user.role.contains("," + Role.ADMIN.name() + ","));
		bb.or(QUser.user.role.startsWith(Role.ADMIN.name() + ","));

		query.where(QUser.user.id.ne(id).and(bb));
		return query.notExists();
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	public List<Map<String, String>> readRoles() {
		List<Map<String, String>> result = new ArrayList<>();
		for (Role role : Role.values()) {
			result.add(ImmutableMap.of("name", role.name()));
		}
		return result;
	}

	// Override create and update because @PreAuthorize does not protect methods inherited
	// from the superclass
	@Override
	@Transactional
	public ExtDirectStoreValidationResult<User> create(User newEntity) {
		return super.create(newEntity);
	}

	@Override
	@Transactional
	public ExtDirectStoreValidationResult<User> update(User updatedEntity) {
		return super.update(updatedEntity);
	}

}
