package ch.rasc.eds.starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.rasc.eds.starter.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
	// nothing here
}
