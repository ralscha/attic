package ch.rasc.eds.starter.service;

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
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.eds.starter.bean.User;
import ch.rasc.eds.starter.repository.UserRepository;
import ch.rasc.eds.starter.util.RepositoryUtil;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@ExtDirectMethod(STORE_READ)
	public ExtDirectStoreResult<User> read(ExtDirectStoreReadRequest readRequest) {

		StringFilter nameFilter = readRequest.getFirstFilterForField("name");
		StringFilter departmentFilter = readRequest.getFirstFilterForField("department");

		String name = null;
		if (nameFilter != null) {
			name = nameFilter.getValue();
		}

		String department = null;
		if (departmentFilter != null) {
			department = departmentFilter.getValue();
		}

		Page<User> pageResult;
		Pageable pageRequest = RepositoryUtil.createPageable(readRequest);

		if (StringUtils.hasText(name) && !StringUtils.hasText(department)) {
			pageResult = this.userRepository
					.findByFirstNameStartsWithIgnoreCaseOrLastNameStartsWithIgnoreCaseOrEmailStartsWithIgnoreCase(
							name, name, name, pageRequest);
		}
		else if (!StringUtils.hasText(name) && StringUtils.hasText(department)) {
			pageResult = this.userRepository.findByDepartment(department, pageRequest);
		}
		else if (StringUtils.hasText(name) && StringUtils.hasText(department)) {
			pageResult = this.userRepository.findByNameAndDepartment("^" + name,
					department, pageRequest);
		}
		else {
			pageResult = this.userRepository.findAll(pageRequest);
		}

		return new ExtDirectStoreResult<>(pageResult.getTotalElements(),
				pageResult.getContent());
	}

	@ExtDirectMethod(STORE_MODIFY)
	public ExtDirectStoreResult<User> create(User newUser) {
		newUser.setId(null);
		User insertedUser = this.userRepository.save(newUser);
		return new ExtDirectStoreResult<>(insertedUser);
	}

	@ExtDirectMethod(STORE_MODIFY)
	public ExtDirectStoreResult<User> update(User changedUser) {
		User updatedUser = this.userRepository.save(changedUser);
		return new ExtDirectStoreResult<>(updatedUser);
	}

	@ExtDirectMethod(STORE_MODIFY)
	public void destroy(User destroyedUser) {
		this.userRepository.delete(destroyedUser);
	}

}
