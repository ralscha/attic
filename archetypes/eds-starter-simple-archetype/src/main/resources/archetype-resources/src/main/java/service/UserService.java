#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ${package}.entity.User;
import ${package}.repository.UserRepository;
import ch.rasc.edsutil.RepositoryUtil;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@ExtDirectMethod(STORE_READ)
	public ExtDirectStoreResult<User> read(ExtDirectStoreReadRequest readRequest) {

		Filter nameFilter = readRequest.getFirstFilterForField("filter");

		String name = null;
		if (nameFilter != null) {
			name = ((StringFilter) nameFilter).getValue();
		}

		Page<User> pageResult;
		Pageable pageRequest = RepositoryUtil.createPageable(readRequest);

		if (StringUtils.hasText(name)) {
			pageResult = userRepository
					.findByFirstNameStartsWithIgnoreCaseOrLastNameStartsWithIgnoreCaseOrEmailStartsWithIgnoreCase(
							name, name, name, pageRequest);
		}
		else {
			pageResult = userRepository.findAll(pageRequest);
		}

		return new ExtDirectStoreResult<>(pageResult.getTotalElements(),
				pageResult.getContent());
	}

	@ExtDirectMethod(STORE_MODIFY)
	public ExtDirectStoreResult<User> create(User newUser) {
		User insertedUser = userRepository.save(newUser);
		return new ExtDirectStoreResult<>(insertedUser);
	}

	@ExtDirectMethod(STORE_MODIFY)
	public ExtDirectStoreResult<User> update(User changedUser) {
		User updatedUser = userRepository.save(changedUser);
		return new ExtDirectStoreResult<>(updatedUser);
	}

	@ExtDirectMethod(STORE_MODIFY)
	public void destroy(User destroyedUser) {
		userRepository.delete(destroyedUser);
	}

}
