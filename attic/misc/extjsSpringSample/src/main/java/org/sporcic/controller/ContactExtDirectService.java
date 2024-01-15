/**
 * Copyright 2010 Ralph Schaer <ralphschaer@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sporcic.controller;

import java.util.List;

import org.sporcic.domain.Contact;
import org.sporcic.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResponse;

@Service
public class ContactExtDirectService {

    @Autowired
    private ContactService contactService;

    @ExtDirectMethod(value = ExtDirectMethodType.STORE_READ)
    public ExtDirectStoreResponse<Contact> read(
            ExtDirectStoreReadRequest request) {
        return new ExtDirectStoreResponse<Contact>(
                contactService.getAllContacts());
    }

    @ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY)
    public List<Contact> create(List<Contact> contacts) {
        return contactService.addContacts(contacts);
    }

    @ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY)
    public List<Contact> update(List<Contact> modifiedContacts) {
        return contactService.updateContacts(modifiedContacts);
    }

    @ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY)
    public List<Integer> destroy(List<Integer> contactIds) {
        contactService.deleteContacts(contactIds);
        return contactIds;
    }

}
