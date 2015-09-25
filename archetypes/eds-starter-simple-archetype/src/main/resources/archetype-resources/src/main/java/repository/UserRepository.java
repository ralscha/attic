#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ${package}.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByDepartmentOrderByLastNameAsc(String department);

	Page<User> findByFirstNameStartsWithIgnoreCaseOrLastNameStartsWithIgnoreCaseOrEmailStartsWithIgnoreCase(
			String firstName, String lastName, String email, Pageable pageable);

}
