package ch.rasc.eds.starter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.rasc.eds.starter.bean.Department;

public interface DepartmentRepository extends MongoRepository<Department, String> {
	// nothing here
}
