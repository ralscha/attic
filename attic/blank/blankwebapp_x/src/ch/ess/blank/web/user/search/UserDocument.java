package ch.ess.blank.web.user.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import ch.ess.blank.db.User;
import ch.ess.common.Constants;
import ch.ess.common.search.SearchEngine;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 */
public class UserDocument {

  private Document document;

  public UserDocument(User user) {
    document = new Document();

    document.add(Field.Keyword(Constants.SEARCH_ID, user.getId().toString()));
    addSearchFields(user);
  }

  public Document getDocument() {
    return document;
  }

  private void addSearchFields(User user) {
    addField(user.getFirstName());
    addField(user.getName());
    addField(user.getUserName());
    addField(user.getEmail());
  }

  private void addField(String value) {
    if (value != null) {
      document.add(Field.UnStored(Constants.SEARCH_TEXT, SearchEngine.removeTermChars(value).toLowerCase()));
    }
  }

}