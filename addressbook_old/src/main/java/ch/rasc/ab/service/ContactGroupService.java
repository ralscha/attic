package ch.rasc.ab.service;

import org.springframework.stereotype.Service;

import ch.rasc.ab.base.BaseCRUDService;
import ch.rasc.ab.entity.ContactGroup;

@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ContactGroupService extends BaseCRUDService<ContactGroup> {
	// nothing here right now
}
