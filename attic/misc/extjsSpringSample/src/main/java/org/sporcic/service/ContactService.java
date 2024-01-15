package org.sporcic.service;

import org.sporcic.domain.Contact;

import java.util.List;

/**
 * Interface for the Contact Service
 */
public interface ContactService {

    public Contact findById(Integer id);

    public List<Contact> searchForContact(String criteria);

    public List<Contact> getAllContacts();

    public List<Contact> addContacts(List<Contact> contacts);

    public List<Contact> updateContacts(List<Contact> contacts);

    public void deleteContacts(List<Integer> ids);
}
