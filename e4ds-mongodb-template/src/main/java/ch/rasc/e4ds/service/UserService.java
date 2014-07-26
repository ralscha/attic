package ch.rasc.e4ds.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.FORM_POST;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.jasypt.springsecurity3.authentication.encoding.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.e4ds.domain.User;
import ch.rasc.e4ds.repository.UserCustomRepository;
import ch.rasc.e4ds.util.Util;

import com.google.common.collect.Lists;

@Service
@Lazy
public class UserService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private UserCustomRepository userCustomRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSource messageSource;

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ExtDirectStoreReadResult<User> load(ExtDirectStoreReadRequest request) {

		String filterValue = null;
		if (!request.getFilters().isEmpty()) {
			StringFilter filter = (StringFilter) request.getFilters().iterator().next();
			filterValue = filter.getValue();
		}

		Page<User> page = userCustomRepository.findWithFilter(filterValue, Util.createPageRequest(request));
		return new ExtDirectStoreReadResult<>((int) page.getTotalElements(), page.getContent());
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	public List<String> loadAllRoles() {
		return Lists.newArrayList("ROLE_ADMIN", "ROLE_USER");
	}

	@ExtDirectMethod(STORE_MODIFY)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void destroy(List<User> destroyUsers) {
		for (User user : destroyUsers) {
			mongoTemplate.remove(user);
		}
	}

	@ExtDirectMethod(FORM_POST)
	@PreAuthorize("isAuthenticated()")
	public ExtDirectFormPostResult userFormPost(Locale locale,
			@RequestParam(required = false, defaultValue = "false") final boolean options,
			@Valid final User modifiedUser, BindingResult bindingResult) {

		// Check uniqueness of userName and email
		if (!bindingResult.hasErrors()) {
			if (!options) {
				Criteria criteria = Criteria.where("userName").is(modifiedUser.getUserName());
				if (StringUtils.hasText(modifiedUser.getId())) {
					criteria = criteria.and("id").ne(modifiedUser.getId());
				}

				if (mongoTemplate.findOne(Query.query(criteria), User.class) != null) {
					bindingResult.rejectValue("userName", null,
							messageSource.getMessage("user_usernametaken", null, locale));
				}
			}

			Criteria criteria = Criteria.where("email").is(modifiedUser.getEmail());
			if (!options) {
				if (StringUtils.hasText(modifiedUser.getId())) {
					criteria = criteria.and("id").ne(modifiedUser.getId());
				}
			} else {
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (principal instanceof MongoDBUserDetails) {
					String userName = ((MongoDBUserDetails) principal).getUsername();
					criteria = criteria.and("userName").ne(userName);
				}
			}

			if (mongoTemplate.findOne(Query.query(criteria), User.class) != null) {
				bindingResult.rejectValue("email", null, messageSource.getMessage("user_emailtaken", null, locale));
			}
		}

		if (!bindingResult.hasErrors()) {

			if (StringUtils.hasText(modifiedUser.getPasswordHash())) {
				modifiedUser.setPasswordHash(passwordEncoder.encodePassword(modifiedUser.getPasswordHash(), null));
			}

			if (StringUtils.hasText(modifiedUser.getId())) {
				User dbUser = mongoTemplate.findById(ObjectId.massageToObjectId(modifiedUser.getId()), User.class);
				if (dbUser != null) {
					dbUser.update(modifiedUser, options);
					mongoTemplate.save(dbUser);
				}
			} else {
				modifiedUser.setId(null);
				mongoTemplate.save(modifiedUser);
			}
		}

		return new ExtDirectFormPostResult(bindingResult);
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	public User getLoggedOnUserObject() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof MongoDBUserDetails) {
			return mongoTemplate.findOne(
					Query.query(Criteria.where("userName").is(((MongoDBUserDetails) principal).getUsername())),
					User.class);
		}
		return null;
	}
}
