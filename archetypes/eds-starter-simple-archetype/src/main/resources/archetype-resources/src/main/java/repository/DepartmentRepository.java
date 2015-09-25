#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ${package}.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
	// nothing here
}
