package ch.rasc.ab.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.ab.entity.Contact;
import ch.rasc.ab.entity.ContactInfo;
import ch.rasc.ab.entity.QContactInfo;
import ch.rasc.edsutil.BaseCRUDService;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class ContactInfoService extends BaseCRUDService<ContactInfo> {

	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<ContactInfo> readContactInfo(
			ExtDirectStoreReadRequest request, long contactId) {
		JPQLQuery query = new JPAQuery(entityManager).from(QContactInfo.contactInfo);
		query.where(QContactInfo.contactInfo.contact.id.eq(contactId));
		addPagingAndSorting(request, query, createPathBuilder());

		SearchResults<ContactInfo> result = query.listResults(QContactInfo.contactInfo);
		return new ExtDirectStoreResult<>(result.getTotal(), result.getResults());
	}

	@Override
	protected void preModify(ContactInfo entity) {

		if (entity.getContactId() != null) {
			entity.setContact(entityManager.getReference(Contact.class,
					entity.getContactId()));
		}

	}

}
