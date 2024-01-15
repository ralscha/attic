package ch.rasc.changelog.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.changelog.entity.Customer;
import ch.rasc.changelog.entity.CustomerBuild;
import ch.rasc.changelog.entity.Documents;
import ch.rasc.changelog.entity.InstallationType;
import ch.rasc.changelog.entity.QCustomer;
import ch.rasc.changelog.entity.QCustomerBuild;
import ch.rasc.edsutil.QueryUtil;

@Service
@Controller
public class CustomerService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private Environment environment;

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<Customer> load(ExtDirectStoreReadRequest request,
			@RequestParam(required = false) Boolean build) {

		BooleanBuilder bb = new BooleanBuilder();
		if (!request.getFilters().isEmpty()) {
			StringFilter filter = (StringFilter) request.getFilters().iterator().next();
			if (filter != null && StringUtils.hasText(filter.getValue())) {
				String value = filter.getValue().toLowerCase();
				String likeValue = "%" + filter.getValue().toLowerCase() + "%";
				if (value.equals("-")) {
					bb.andNot(QCustomer.customer.features.lower().like("%=true%"));
				}
				else {
					bb.or(QCustomer.customer.features.lower().like(likeValue));
				}
				bb.or(QCustomer.customer.longName.lower().like(likeValue));
				bb.or(QCustomer.customer.shortName.lower().like(likeValue));

			}
		}

		JPQLQuery query = new JPAQuery(this.entityManager).from(QCustomer.customer)
				.where(bb);

		if (BooleanUtils.toBoolean(build)) {
			String ignoreCustomers = this.environment
					.getProperty("build.ignore.customer");
			if (StringUtils.hasText(ignoreCustomers)) {
				Set<String> ignoreCustomersSet = Sets
						.newHashSet(Splitter.on(";").split(ignoreCustomers));
				query.where(QCustomer.customer.shortName.notIn(ignoreCustomersSet));
			}
		}

		QueryUtil.addPagingAndSorting(query, request, Customer.class, QCustomer.customer);

		List<Customer> customers = query.list(QCustomer.customer);
		long total = query.count();

		for (Customer customer : customers) {
			updateLatest(customer);
		}

		return new ExtDirectStoreResult<>(total, customers);
	}

	private void updateLatest(Customer customer) {
		if (!customer.getBuilds().isEmpty()) {
			CustomerBuild lastBuild = getLatestBuild(customer);
			customer.setLatestVersionDate(lastBuild.getVersionDate());
			String versionNumber = lastBuild.getVersionNumber();
			if (BooleanUtils.toBoolean(lastBuild.getInternalBuild())) {
				versionNumber += " (I)";
			}
			customer.setLatestVersionNumber(versionNumber);
		}
	}

	@Transactional(readOnly = true)
	public CustomerBuild getLatestBuild(Customer customer) {
		return new JPAQuery(this.entityManager).from(QCustomerBuild.customerBuild)
				.where(QCustomerBuild.customerBuild.customer.eq(customer))
				.orderBy(QCustomerBuild.customerBuild.versionDate.desc(),
						QCustomerBuild.customerBuild.id.desc())
				.singleResult(QCustomerBuild.customerBuild);
	}

	@ExtDirectMethod(STORE_MODIFY)
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public void destroy(List<Customer> destroyCustomers) {
		for (Customer customer : destroyCustomers) {
			this.entityManager
					.remove(this.entityManager.find(Customer.class, customer.getId()));
		}
	}

	@ExtDirectMethod(STORE_MODIFY)
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public List<Customer> create(List<Customer> newCustomers) {
		List<Customer> insertedCustomers = new ArrayList<>();

		for (Customer newCustomer : newCustomers) {
			newCustomer.setId(null);
			newCustomer.updateFeatures();
			this.entityManager.persist(newCustomer);
			insertedCustomers.add(newCustomer);
		}

		return insertedCustomers;
	}

	@ExtDirectMethod(STORE_MODIFY)
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public List<Customer> update(List<Customer> modifiedCustomers) {
		List<Customer> updatedRecords = new ArrayList<>();
		for (Customer modifiedCustomer : modifiedCustomers) {
			Customer dbCustomer = this.entityManager.find(Customer.class,
					modifiedCustomer.getId());
			if (dbCustomer != null) {
				dbCustomer.update(modifiedCustomer);
				dbCustomer.updateFeatures();
				updateLatest(dbCustomer);
				updatedRecords.add(dbCustomer);
			}
		}
		return updatedRecords;
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public Set<CustomerBuild> loadBuilds(Long customerId) {
		Customer customer = this.entityManager.find(Customer.class, customerId);
		Set<CustomerBuild> builds = customer.getBuilds();
		Hibernate.initialize(builds);
		return builds;
	}

	@ExtDirectMethod(STORE_MODIFY)
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public void destroyBuild(List<CustomerBuild> destroyCustomerBuilds) {
		for (CustomerBuild build : destroyCustomerBuilds) {
			Customer customer = this.entityManager.find(Customer.class,
					build.getCustomerId());
			customer.getBuilds().remove(build);
		}
	}

	@ExtDirectMethod(STORE_MODIFY)
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public List<CustomerBuild> createBuild(List<CustomerBuild> newCustomerBuilds) {
		List<CustomerBuild> inserted = new ArrayList<>();

		for (CustomerBuild build : newCustomerBuilds) {
			Customer customer = this.entityManager.find(Customer.class,
					build.getCustomerId());

			CustomerBuild newBuild = new CustomerBuild();
			newBuild.setCustomer(customer);
			newBuild.setVersionDate(build.getVersionDate());
			newBuild.setVersionNumber(build.getVersionNumber());
			newBuild.setInternalBuild(build.getInternalBuild());

			customer.getBuilds().add(newBuild);

			inserted.add(newBuild);
		}

		return inserted;
	}

	@ExtDirectMethod(STORE_MODIFY)
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public List<CustomerBuild> updateBuild(List<CustomerBuild> modifiedCustomerBuilds) {
		List<CustomerBuild> updated = new ArrayList<>();
		for (CustomerBuild build : modifiedCustomerBuilds) {
			CustomerBuild dbBuild = this.entityManager.find(CustomerBuild.class,
					build.getId());
			if (dbBuild != null) {
				dbBuild.setVersionNumber(build.getVersionNumber());
				dbBuild.setVersionDate(build.getVersionDate());
				dbBuild.setInternalBuild(build.getInternalBuild());
			}
		}
		return updated;
	}

	@Transactional(readOnly = true)
	public String getNextVersionNumber(final String customer) {

		Customer c = getCustomer(customer);
		if (c != null) {

			int nextMajor = Integer
					.parseInt(this.environment.getProperty("next.version.major"));
			int nextMinor = Integer
					.parseInt(this.environment.getProperty("next.version.minor"));

			CustomerBuild latestCustomerBuild = getLatestBuild(c);
			if (latestCustomerBuild != null) {
				String latestVersion = latestCustomerBuild.getVersionNumber();

				if (org.apache.commons.lang3.StringUtils.isNotBlank(latestVersion)) {
					List<String> latestVersionSplitted = Splitter.on(".")
							.splitToList(latestVersion);
					int latestMajor = Integer.parseInt(latestVersionSplitted.get(0));
					int latestMinor = Integer.parseInt(latestVersionSplitted.get(1));
					int latestBuild = Integer.parseInt(latestVersionSplitted.get(2));

					if (nextMajor <= latestMajor && nextMinor <= latestMinor) {
						return String.format("%s.%s.%s", latestMajor, latestMinor,
								latestBuild + 1);
					}
				}
			}

			return String.format("%s.%s.0", nextMajor, nextMinor);
		}

		return null;
	}

	@Transactional
	public void addCustomerBuild(final String customer, final String version,
			final Boolean internalBuild) {

		Customer c = getCustomer(customer);
		if (c != null) {

			Set<CustomerBuild> builds = c.getBuilds();

			boolean found = false;
			for (CustomerBuild build : builds) {
				if (build.getVersionNumber().equals(version)) {
					found = true;
					build.setVersionDate(new Date());
					break;
				}
			}

			if (!found) {
				CustomerBuild newBuild = new CustomerBuild();
				newBuild.setCustomer(c);
				newBuild.setVersionNumber(version);
				newBuild.setVersionDate(new Date());
				newBuild.setInternalBuild(internalBuild);
				builds.add(newBuild);
			}

		}

	}

	@Transactional(readOnly = true)
	public String getFtpUserName(String customer) {
		Customer c = getCustomer(customer);
		if (c != null) {
			return c.getFtpUser();
		}
		return null;
	}

	@Transactional(readOnly = true)
	public InstallationType getInstallation(String customer) {
		Customer c = getCustomer(customer);
		if (c != null) {
			return c.getInstallation();
		}
		return null;
	}

	private Customer getCustomer(String customer) {
		Customer c = new JPAQuery(this.entityManager).from(QCustomer.customer)
				.where(QCustomer.customer.shortName.eq(customer))
				.singleResult(QCustomer.customer);
		return c;
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public Set<Documents> loadDocuments(Long customerId) {
		Customer customer = this.entityManager.find(Customer.class, customerId);
		Set<Documents> documents = customer.getDocuments();
		Hibernate.initialize(documents);
		return documents;
	}

	@ExtDirectMethod(STORE_MODIFY)
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public void destroyDocument(List<Documents> documents) {
		for (Documents document : documents) {
			Customer customer = this.entityManager.find(Customer.class,
					document.getCustomerId());
			customer.getDocuments().remove(document);
		}
	}
}
