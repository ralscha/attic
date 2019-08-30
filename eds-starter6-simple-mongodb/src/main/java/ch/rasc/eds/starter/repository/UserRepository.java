package ch.rasc.eds.starter.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ch.rasc.eds.starter.bean.User;

public interface UserRepository extends MongoRepository<User, String> {

	List<User> findByDepartmentOrderByLastNameAsc(String department);

	Page<User> findByFirstNameStartsWithIgnoreCaseOrLastNameStartsWithIgnoreCaseOrEmailStartsWithIgnoreCase(
			String firstName, String lastName, String email, Pageable pageable);

	Page<User> findByDepartment(String department, Pageable pageRequest);

	@Query("{ department: ?1, $or : [ { firstName : { $regex : ?0 , $options : 'i'}} , { lastName : { $regex : ?0 , $options : 'i'}}, { email : { $regex : ?0 , $options : 'i'}} ]}")
	Page<User> findByNameAndDepartment(String firstName, String department,
			Pageable pageRequest);

}
