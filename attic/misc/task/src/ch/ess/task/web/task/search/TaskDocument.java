package ch.ess.task.web.task.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import ch.ess.common.Constants;
import ch.ess.common.search.SearchEngine;
import ch.ess.task.db.Task;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class TaskDocument {

  private Document document;

  public TaskDocument(Task task) {
    document = new Document();

    document.add(Field.Keyword(Constants.SEARCH_ID, task.getId().toString()));
    addSearchFields(task);
  }

  public Document getDocument() {
    return document;
  }

  private void addSearchFields(Task task) {
    addField(task.getName());
    addField(task.getResolution());
    addField(task.getDescription());
  }

  private void addField(String value) {
    if (value != null) {
      document.add(Field.UnStored(Constants.SEARCH_TEXT, SearchEngine.removeTermChars(value).toLowerCase()));
    }
  }

}
