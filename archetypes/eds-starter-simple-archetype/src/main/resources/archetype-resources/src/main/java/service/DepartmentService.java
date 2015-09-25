#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ${package}.entity.Department;
import ${package}.repository.DepartmentRepository;

@Service
public class DepartmentService {

	private final DepartmentRepository departmentRepository;

	@Autowired
	public DepartmentService(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@ExtDirectMethod(STORE_READ)
	public List<Department> read() {
		return departmentRepository.findAll();
	}

}
