package ch.ess.addressbook.web.contact.search;

import java.util.Iterator;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import ch.ess.addressbook.db.Contact;
import ch.ess.common.Constants;
import ch.ess.common.search.SearchEngine;


public class ContactDocument {

  private Document document;

  public ContactDocument(Contact contact) {
    document = new Document();


    document.add(Field.Keyword(Constants.SEARCH_ID, contact.getId().toString()));
    addSearchFields(contact);    
  }

  public Document getDocument() {
    return document;
  }

  private void addSearchFields(Contact contact) {

    Map attributes = contact.getAttributes();

    if (attributes != null) {
      Iterator it = attributes.values().iterator();
      while (it.hasNext()) {
        String value = (String) it.next();
        addField(value);
      }

    }

  }

  private void addField(String value) {
    if (value != null) {
      document.add(Field.UnStored(Constants.SEARCH_TEXT, SearchEngine.removeTermChars(value).toLowerCase()));
    }
  }

}


