package ar.com.koalas.providers.taglibs.comboselect;

import java.util.Iterator;

/**
 * Holds the select data to be used in ComboSelect
 * @author Guille
 */
public class SelectData {
  @SuppressWarnings("unchecked")
  public SelectData(Iterator iterator, String key, String description) {
    this.iterator = iterator;
    this.description = description;
    this.key = key;
  }

  /**
   * @return Returns the description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return Returns the iterator.
   */
  @SuppressWarnings("unchecked")
  public Iterator getIterator() {
    return iterator;
  }

  @SuppressWarnings("unchecked")
  public void setIterator(Iterator it) {
    this.iterator = it;
  }

  /**
   * @return Returns the key.
   */
  public String getKey() {
    return key;
  }

  @SuppressWarnings("unchecked")
  private Iterator iterator;
  private String key;
  private String description;
}
