package ch.ess.base.web;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author sr
 */
public class AbstractNameEntryForm extends AbstractForm {

  private NameEntry[] entries;

  public NameEntry[] getEntry() {
    return (NameEntry[])ArrayUtils.clone(entries);
  }

  public void setEntry(final NameEntry[] entries) {
    this.entries = (NameEntry[])ArrayUtils.clone(entries);
  }

  public void setEntry(final int index, final NameEntry entry) {
    entries[index] = entry;
  }

  public NameEntry getEntry(final int index) {
    return this.entries[index];
  }

}
