package ch.rasc.ab.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.NumericFilter;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.ab.base.BaseCRUDService;
import ch.rasc.ab.entity.Contact;
import ch.rasc.ab.entity.ContactGroup;

@Service
@PreAuthorize("hasRole('ROLE_USER')")
public class ContactService extends BaseCRUDService<Contact> {

	@Override
	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<Contact> read(ExtDirectStoreReadRequest request) {

		JPQLQuery query = new JPAQuery(this.entityManager).from(QContact.contact);
		StringFilter filter = (StringFilter) request.getFirstFilterForField("filter");
		if (filter != null) {
			String value = filter.getValue().trim();
			BooleanBuilder bb = new BooleanBuilder();
			bb.or(QContact.contact.lastName.contains(value));
			bb.or(QContact.contact.firstName.contains(value));

			query.where(bb);
		}

		NumericFilter nFilter = (NumericFilter) request.getFirstFilterForField("group");
		if (nFilter != null) {
			query.innerJoin(QContact.contact.contactGroups, QContactGroup.contactGroup);
			query.where(QContactGroup.contactGroup.id.eq(nFilter.getValue().longValue()));
		}

		ch.rasc.ab.util.Util.addPagingAndSorting(query, request, Contact.class,
				QContact.contact);
		SearchResults<Contact> searchResult = query.listResults(QContact.contact);

		return new ExtDirectStoreResult<>(searchResult.getTotal(),
				searchResult.getResults());
	}

	@Override
	protected void preModify(Contact entity) {
		if (entity.getContactGroupIds() != null) {
			Set<Long> dbContactGroupIds = new HashSet<>();

			if (entity.getId() != null) {
				Contact dbContact = this.entityManager.find(Contact.class,
						entity.getId());
				entity.setContactGroups(dbContact.getContactGroups());
			}

			for (ContactGroup group : entity.getContactGroups()) {
				dbContactGroupIds.add(group.getId());
			}

			Set<Long> groupIds = new HashSet<>();
			for (Long groupId : entity.getContactGroupIds()) {
				if (groupId != null) {
					groupIds.add(groupId);
				}
			}

			SetView<Long> removeGroupIds = Sets.difference(dbContactGroupIds, groupIds);
			SetView<Long> newGroupIds = Sets.difference(groupIds, dbContactGroupIds);

			for (Long newGroupId : newGroupIds) {
				ContactGroup contactGroup = this.entityManager
						.getReference(ContactGroup.class, newGroupId);
				entity.getContactGroups().add(contactGroup);
			}

			for (Long removeGroupId : removeGroupIds) {
				ContactGroup contactGroup = this.entityManager
						.getReference(ContactGroup.class, removeGroupId);
				entity.getContactGroups().remove(contactGroup);
			}
		}
	}
}
