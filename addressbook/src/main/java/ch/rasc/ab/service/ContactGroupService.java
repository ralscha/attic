package ch.rasc.ab.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import ch.rasc.ab.entity.ContactGroup;
import ch.rasc.edsutil.BaseCRUDService;

@Service
@PreAuthorize("hasRole('ADMIN')")
public class ContactGroupService extends BaseCRUDService<ContactGroup> {
	// nothing here right now
}
