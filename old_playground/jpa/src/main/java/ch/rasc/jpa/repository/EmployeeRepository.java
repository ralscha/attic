package ch.rasc.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.jpa.model.Employee;

@Repository
public class EmployeeRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<Employee> getAllEmployees() {
		CriteriaQuery<Employee> criteria = this.entityManager.getCriteriaBuilder()
				.createQuery(Employee.class);
		criteria.select(criteria.from(Employee.class));

		TypedQuery<Employee> query = this.entityManager.createQuery(criteria);
		return query.getResultList();
	}

	@Transactional
	public Employee update(Employee employee) {
		return this.entityManager.merge(employee);
	}
}
