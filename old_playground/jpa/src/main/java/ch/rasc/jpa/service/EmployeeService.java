package ch.rasc.jpa.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ch.rasc.jpa.model.Address;
import ch.rasc.jpa.model.Employee;
import ch.rasc.jpa.model.QEmployee;

@Service
public class EmployeeService {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public void query() {
		System.out.println("lookup address");
		Address a = this.entityManager.find(Address.class, 1L);

		System.out.println("contains query");
		List<Employee> employees = new JPAQueryFactory(this.entityManager)
				.selectFrom(QEmployee.employee)
				.where(QEmployee.employee.address.contains(a)).fetch();
		for (Employee employee : employees) {
			System.out.println(employee);
		}
	}

	@Transactional(readOnly = true)
	public void create() {
		Employee e = new Employee();
		e.setEmployeeId(1L);
		e.setEmployeeName("Doe");
		e.setEmployeeSurname("John");
		e.setJob("Boss");

		Address address = new Address();
		address.setEmployee(e);
		address.setStreet("a street");
		e.getAddress().add(address);

		this.entityManager.persist(e);
	}

	@Transactional(readOnly = true)
	public void update() {
		Employee e = this.entityManager.find(Employee.class, 1L);
		e.setEmployeeName("test");
	}

	@Transactional(readOnly = true)
	public void remove() {
		Employee e = this.entityManager.find(Employee.class, 1L);
		this.entityManager.remove(e);
	}

}
