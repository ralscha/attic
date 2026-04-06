package ch.rasc.eds.starter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.rasc.eds.starter.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByDepartmentNameOrderByLastNameAsc(String departmentName);

}
