package ch.rasc.eds.starter.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.eds.starter.entity.Department;
import ch.rasc.eds.starter.entity.User;
import ch.rasc.eds.starter.repository.DepartmentRepository;
import ch.rasc.eds.starter.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	private final DepartmentRepository departmentRepository;

	public UserService(UserRepository userRepository,
			DepartmentRepository departmentRepository) {
		this.userRepository = userRepository;
		this.departmentRepository = departmentRepository;
	}

	@ExtDirectMethod(STORE_READ)
	public List<User> read() {
		return this.userRepository.findAll().stream().peek(u -> {
			if (u.getDepartment() != null) {
				u.setDepartmentId(u.getDepartment().getId());
			}
		}).collect(Collectors.toList());
	}

	@ExtDirectMethod(STORE_MODIFY)
	public User create(User newUser) {
		if (newUser.getDepartmentId() > 0) {
			newUser.setDepartment(
					this.departmentRepository.getOne(newUser.getDepartmentId()));
		}
		return this.userRepository.save(newUser);
	}

	@ExtDirectMethod(STORE_MODIFY)
	public User update(User changedUser) {
		Department department = this.departmentRepository
				.findById(changedUser.getDepartmentId()).orElse(null);

		changedUser.setDepartment(department);
		User savedUser = this.userRepository.save(changedUser);

		if (department != null) {
			savedUser.setDepartmentId(department.getId());
		}

		return savedUser;
	}

	@ExtDirectMethod(STORE_MODIFY)
	public void destroy(User destroyedUser) {
		this.userRepository.delete(destroyedUser);
	}

}
