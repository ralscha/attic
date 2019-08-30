package ch.rasc.eds.starter.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.NumericFilter;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.eds.starter.entity.User;
import ch.rasc.eds.starter.repository.DepartmentRepository;
import ch.rasc.eds.starter.repository.UserRepository;
import ch.rasc.eds.starter.util.RepositoryUtil;

@Service
public class UserService {

	private final UserRepository userRepository;

	private final DepartmentRepository departmentRepository;

	@Autowired
	public UserService(UserRepository userRepository,
			DepartmentRepository departmentRepository) {
		this.userRepository = userRepository;
		this.departmentRepository = departmentRepository;
	}

	@ExtDirectMethod(STORE_READ)
	public ExtDirectStoreResult<User> read(ExtDirectStoreReadRequest readRequest) {

		StringFilter nameFilter = readRequest.getFirstFilterForField("name");
		Filter departmentFilter = readRequest.getFirstFilterForField("department");

		String name = null;
		if (nameFilter != null) {
			name = nameFilter.getValue();
		}

		long department = -1;
		if (departmentFilter != null && departmentFilter instanceof NumericFilter) {
			department = ((NumericFilter) departmentFilter).getValue().longValue();
		}

		Page<User> pageResult;
		Pageable pageRequest = RepositoryUtil.createPageable(readRequest);

		if (StringUtils.hasText(name) && department == -1) {
			pageResult = this.userRepository
					.findByFirstNameStartsWithIgnoreCaseOrLastNameStartsWithIgnoreCaseOrEmailStartsWithIgnoreCase(
							name, name, name, pageRequest);
		}
		else if (!StringUtils.hasText(name) && department != -1) {
			pageResult = this.userRepository.findByDepartment_Id(department, pageRequest);
		}
		else if (StringUtils.hasText(name) && department != -1) {
			pageResult = this.userRepository.findByNameAndDepartmentId(name + "%",
					department, pageRequest);
		}
		else {
			pageResult = this.userRepository.findAll(pageRequest);
		}

		return new ExtDirectStoreResult<>(pageResult.getTotalElements(),
				pageResult.getContent().stream()
						.peek(u -> u.setDepartmentId(u.getDepartment().getId()))
						.collect(Collectors.toList()));
	}

	@ExtDirectMethod(STORE_MODIFY)
	public ExtDirectStoreResult<User> create(User newUser) {
		if (newUser.getDepartmentId() > 0) {
			newUser.setDepartment(
					this.departmentRepository.getOne(newUser.getDepartmentId()));
		}
		User insertedUser = this.userRepository.save(newUser);
		if (insertedUser.getDepartment() != null) {
			insertedUser.setDepartmentId(insertedUser.getDepartment().getId());
		}
		return new ExtDirectStoreResult<>(insertedUser);
	}

	@ExtDirectMethod(STORE_MODIFY)
	public ExtDirectStoreResult<User> update(User changedUser) {
		changedUser.setDepartment(
				this.departmentRepository.getOne(changedUser.getDepartmentId()));
		User updatedUser = this.userRepository.save(changedUser);
		if (updatedUser.getDepartment() != null) {
			updatedUser.setDepartmentId(updatedUser.getDepartment().getId());
		}
		return new ExtDirectStoreResult<>(updatedUser);
	}

	@ExtDirectMethod(STORE_MODIFY)
	public void destroy(User destroyedUser) {
		this.userRepository.delete(destroyedUser);
	}

}
