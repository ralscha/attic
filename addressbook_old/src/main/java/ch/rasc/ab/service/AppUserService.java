package ch.rasc.ab.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.ab.base.BaseCRUDService;
import ch.rasc.ab.base.ValidationError;
import ch.rasc.ab.entity.AppUser;

@Service
@Lazy
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AppUserService extends BaseCRUDService<AppUser> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<AppUser> read(ExtDirectStoreReadRequest request) {

		JPQLQuery query = new JPAQuery(this.entityManager).from(QAppUser.appUser);
		if (!request.getFilters().isEmpty()) {
			StringFilter filter = (StringFilter) request.getFilters().iterator().next();

			BooleanBuilder bb = new BooleanBuilder();
			bb.or(QAppUser.appUser.userName.contains(filter.getValue()));
			bb.or(QAppUser.appUser.name.contains(filter.getValue()));
			bb.or(QAppUser.appUser.firstName.contains(filter.getValue()));
			bb.or(QAppUser.appUser.email.contains(filter.getValue()));

			query.where(bb);
		}

		ch.rasc.ab.util.Util.addPagingAndSorting(query, request, AppUser.class,
				QAppUser.appUser);
		SearchResults<AppUser> searchResult = query.listResults(QAppUser.appUser);

		return new ExtDirectStoreResult<>(searchResult.getTotal(),
				searchResult.getResults());
	}

	@Override
	protected List<ValidationError> validateEntity(AppUser entity) {
		List<ValidationError> validations = super.validateEntity(entity);

		if (entity.getPasswordNew() != null
				&& !entity.getPasswordNew().equals(entity.getPasswordNewConfirm())) {
			ValidationError validationError = new ValidationError();
			validationError.setField("passwordNew");
			validationError.setMessage("Passwords do not match");
			validations.add(validationError);
		}

		// Check uniqueness of userName and email
		if (StringUtils.hasText(entity.getUserName())) {
			BooleanBuilder bb = new BooleanBuilder(
					QAppUser.appUser.userName.equalsIgnoreCase(entity.getUserName()));
			if (entity.getId() != null) {
				bb.and(QAppUser.appUser.id.ne(entity.getId()));
			}
			if (new JPAQuery(this.entityManager).from(QAppUser.appUser).where(bb)
					.exists()) {
				ValidationError validationError = new ValidationError();
				validationError.setField("userName");
				validationError.setMessage("Username already taken");
				validations.add(validationError);
			}
		}

		if (StringUtils.hasText(entity.getEmail())) {
			BooleanBuilder bb = new BooleanBuilder(
					QAppUser.appUser.email.equalsIgnoreCase(entity.getEmail()));
			if (entity.getId() != null) {
				bb.and(QAppUser.appUser.id.ne(entity.getId()));
			}
			if (new JPAQuery(this.entityManager).from(QAppUser.appUser).where(bb)
					.exists()) {
				ValidationError validationError = new ValidationError();
				validationError.setField("email");
				validationError.setMessage("E-Mail already taken");
				validations.add(validationError);
			}
		}

		return validations;
	}

	@Override
	protected void preModify(AppUser entity) {
		super.preModify(entity);

		if (StringUtils.hasText(entity.getPasswordNew())) {
			entity.setPasswordHash(this.passwordEncoder.encode(entity.getPasswordNew()));
		}
	}

	@Override
	@Transactional
	public ExtDirectStoreResult<AppUser> destroy(Long id) {
		if (!isLastUser(id)) {
			return super.destroy(id);
		}

		ExtDirectStoreResult<AppUser> result = new ExtDirectStoreResult<>();
		result.setSuccess(false);
		return result;
	}

	private boolean isLastUser(Long id) {
		return new JPAQuery(this.entityManager).from(QAppUser.appUser)
				.where(QAppUser.appUser.id.ne(id)).notExists();
	}
}
