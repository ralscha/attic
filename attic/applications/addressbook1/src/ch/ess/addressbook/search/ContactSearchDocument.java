package ch.ess.addressbook.search;

import java.util.*;

import org.apache.lucene.document.*;

import ch.ess.addressbook.db.*;


public class ContactSearchDocument {

  private Document document;

  public ContactSearchDocument(Contact contact) {
    document = new Document();


    document.add(Field.Keyword(SearchEngine.ID_FIELD, contact.getId().toString()));
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
      document.add(Field.UnStored(SearchEngine.SEARCH_FIELD, SearchEngine.removeTermChars(value).toLowerCase()));
    }
  }

}


