package ch.rasc.eds.starter.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ch.rasc.eds.starter.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByDepartmentOrderByLastNameAsc(String department);

	Page<User> findByFirstNameStartsWithIgnoreCaseOrLastNameStartsWithIgnoreCaseOrEmailStartsWithIgnoreCase(
			String firstName, String lastName, String email, Pageable pageable);

	Page<User> findByDepartment(String department, Pageable pageRequest);

	@Query("FROM User u WHERE (upper(u.firstName) like upper(?1) or upper(u.lastName) like upper(?1) or upper(u.email) like upper(?1)) and u.department=?2")
	Page<User> findByNameAndDepartment(String name, String department,
			Pageable pageRequest);

}
