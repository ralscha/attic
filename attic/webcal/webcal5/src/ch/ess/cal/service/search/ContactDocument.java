package ch.ess.cal.service.search;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import ch.ess.cal.model.Contact;
import ch.ess.cal.model.ContactAttribute;

public class ContactDocument {
  private Document document;

  public Document getDocument() {
    return document;
  }

  public ContactDocument(Contact contact) {
    document = new Document();
    document.add(new Field("id", contact.getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED));

    if (contact.getContactAttributes() != null) {
      Collection<ContactAttribute> attributes = contact.getContactAttributes().values();
      for (ContactAttribute attribute : attributes) {
        String value = attribute.getValue();
        if (StringUtils.isNotBlank(value)) {
          document.add(new Field("text", value, Field.Store.NO, Field.Index.TOKENIZED));
        }
      }
    }
    
    if (!contact.getUsers().isEmpty()) {
      document.add(new Field("userId", contact.getUsers().iterator().next().getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED));
    } else {
      document.add(new Field("userId", "public", Field.Store.YES, Field.Index.UN_TOKENIZED));
    }

  }

}
