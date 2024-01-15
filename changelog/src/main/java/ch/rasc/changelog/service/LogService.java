package ch.rasc.changelog.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Sets;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.changelog.entity.Change;
import ch.rasc.changelog.entity.Customer;
import ch.rasc.changelog.entity.CustomerBuild;
import ch.rasc.changelog.entity.CustomerChange;
import ch.rasc.changelog.entity.QChange;
import ch.rasc.changelog.entity.QCustomerBuild;

@Service
public class LogService {

	@PersistenceContext
	private EntityManager entityManager;

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public List<Change> loadChanges(
			@RequestParam(required = false) final Long customerBuildId,
			@RequestParam(required = false) final Long customerId) {

		Date versionDate;
		Customer customer;
		boolean starBuild = false;

		if (customerBuildId != null) {
			CustomerBuild build = this.entityManager.find(CustomerBuild.class,
					customerBuildId);
			versionDate = build.getVersionDate();
			customer = build.getCustomer();
		}
		else {
			starBuild = true;
			versionDate = LocalDate.now().toDate();
			customer = this.entityManager.find(Customer.class, customerId);
		}

		// look for previous build
		JPQLQuery query = new JPAQuery(this.entityManager)
				.from(QCustomerBuild.customerBuild);
		query.where(QCustomerBuild.customerBuild.customer.eq(customer));

		if (!starBuild) {
			query.where(QCustomerBuild.customerBuild.versionDate.lt(versionDate));
		}

		query.orderBy(QCustomerBuild.customerBuild.versionDate.desc());
		CustomerBuild previousBuild = query.singleResult(QCustomerBuild.customerBuild);

		List<Change> resultList = new ArrayList<>();

		if (previousBuild != null) {

			for (Change change : new JPAQuery(this.entityManager).from(QChange.change)
					.where(QChange.change.implementationDate
							.gt(previousBuild.getVersionDate())
							.and(QChange.change.implementationDate.loe(versionDate)))
					.orderBy(QChange.change.typ.asc()).list(QChange.change)) {

				if (change.getCustomerChanges().isEmpty()) {
					resultList.add(change);
				}
				else {
					for (CustomerChange cc : change.getCustomerChanges()) {
						if (cc.getCustomer().equals(customer)) {
							resultList.add(change);
							break;
						}
					}
				}

			}
		}

		return resultList;
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public Set<CustomerBuild> loadCustomerBuild(Long customerId) {
		Customer customer = this.entityManager.find(Customer.class, customerId);
		Set<CustomerBuild> builds = Sets.newHashSet(customer.getBuilds());

		CustomerBuild trunkBuild = new CustomerBuild();
		trunkBuild.setCustomer(customer);
		trunkBuild.setVersionDate(LocalDate.now().toDate());
		trunkBuild.setVersionNumber("*");
		builds.add(trunkBuild);

		return builds;
	}

}
