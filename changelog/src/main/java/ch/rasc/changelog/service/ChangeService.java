package ch.rasc.changelog.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.base.Joiner;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.bean.SortDirection;
import ch.ralscha.extdirectspring.bean.SortInfo;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.changelog.entity.Change;
import ch.rasc.changelog.entity.Customer;
import ch.rasc.changelog.entity.CustomerChange;
import ch.rasc.changelog.entity.QChange;
import ch.rasc.edsutil.QueryUtil;

@Service
@Controller
public class ChangeService {

	@PersistenceContext
	private EntityManager entityManager;

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<Change> load(ExtDirectStoreReadRequest request) {

		if (!request.getFilters().isEmpty()) {
			StringFilter filter = (StringFilter) request.getFilters().iterator().next();
			if (StringUtils.hasText(filter.getValue())) {

				FullTextEntityManager fullTextEntityManager = Search
						.getFullTextEntityManager(this.entityManager);
				QueryBuilder qb = fullTextEntityManager.getSearchFactory()
						.buildQueryBuilder().forEntity(Change.class).get();
				Query query = qb.keyword().wildcard().onField("search")
						.matching(filter.getValue().trim().toLowerCase()).createQuery();

				FullTextQuery persistenceQuery = fullTextEntityManager
						.createFullTextQuery(query, Change.class);

				List<SortField> sorts = new ArrayList<>();
				for (SortInfo sortInfo : request.getSorters()) {

					boolean reverse;
					if (sortInfo.getDirection() == SortDirection.ASCENDING) {
						reverse = false;
					}
					else {
						reverse = true;
					}

					sorts.add(new SortField(sortInfo.getProperty(), SortField.STRING,
							reverse));
				}

				persistenceQuery
						.setSort(new Sort(sorts.toArray(new SortField[sorts.size()])));

				persistenceQuery.setFirstResult(request.getStart());
				persistenceQuery.setMaxResults(request.getLimit());
				int total = persistenceQuery.getResultSize();

				List<Change> changes = persistenceQuery.getResultList();
				for (Change change : changes) {
					updateCustomerInformation(change);
				}

				return new ExtDirectStoreResult<>(total, changes);
			}
		}

		JPQLQuery query = new JPAQuery(this.entityManager).from(QChange.change);
		QueryUtil.addPagingAndSorting(query, request, Change.class, QChange.change);

		List<Change> changes = query.list(QChange.change);
		long total = query.count();

		for (Change change : changes) {
			updateCustomerInformation(change);
		}

		return new ExtDirectStoreResult<>(total, changes);
	}

	private static void updateCustomerInformation(Change change) {

		if (change.getCustomerChanges().isEmpty()) {
			change.setCustomerTooltip(null);
			change.setNoOfCustomers(null);
		}
		else {
			change.setNoOfCustomers(change.getCustomerChanges().size());

			Set<String> shortNames = new HashSet<>();
			Set<Long> ids = new HashSet<>();

			for (CustomerChange cc : change.getCustomerChanges()) {
				ids.add(cc.getCustomer().getId());
				shortNames.add(cc.getCustomer().getShortName());
			}
			change.setCustomerTooltip(Joiner.on(",").join(shortNames));
			change.setCustomerIds(ids);
		}
	}

	@ExtDirectMethod(STORE_MODIFY)
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public void destroy(List<Change> destroyChanges) {
		for (Change change : destroyChanges) {
			this.entityManager
					.remove(this.entityManager.find(Change.class, change.getId()));
		}
	}

	@ExtDirectMethod(STORE_MODIFY)
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public List<Change> create(List<Change> newChanges) {
		List<Change> insertedChanges = new ArrayList<>();

		for (Change newChange : newChanges) {
			newChange.setId(null);
			addCustomerChanges(newChange, newChange.getCustomerIds());
			newChange.setSubject(newChange.getSubject().substring(0,
					Math.min(newChange.getSubject().length(), 254)));

			this.entityManager.persist(newChange);
			insertedChanges.add(newChange);
		}

		return insertedChanges;
	}

	private void addCustomerChanges(Change change, Set<Long> customerIds) {
		Set<CustomerChange> customerChanges = new HashSet<>();
		for (Long customerId : customerIds) {
			CustomerChange cc = new CustomerChange();
			cc.setCustomer(this.entityManager.getReference(Customer.class, customerId));
			cc.setChange(change);
			customerChanges.add(cc);
		}
		change.getCustomerChanges().clear();
		change.getCustomerChanges().addAll(customerChanges);
	}

	@ExtDirectMethod(STORE_MODIFY)
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public List<Change> update(List<Change> modifiedChanges) {
		List<Change> updatedRecords = new ArrayList<>();
		for (Change modifiedChange : modifiedChanges) {
			Change dbChange = this.entityManager.find(Change.class,
					modifiedChange.getId());
			if (dbChange != null) {
				dbChange.update(modifiedChange);
				addCustomerChanges(dbChange, modifiedChange.getCustomerIds());
				updateCustomerInformation(dbChange);
				updatedRecords.add(dbChange);
			}
		}
		return updatedRecords;
	}

}
