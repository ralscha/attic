package ch.ess.cal.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.MessageResources;

import ch.ess.cal.model.TranslateObject;
import ch.ess.cal.model.Translation;
import ch.ess.cal.model.TranslationText;
import ch.ess.cal.web.NameEntry;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:33 $ 
 * 
 * @spring.bean id="translationService"
 */
public class TranslationService {

  private Map<String,String> localeData;

  /**    
   * @spring.property ref="localeData"
   */
  public void setLocaleData(final Map<String,String> localeData) {
    this.localeData = localeData;
  }

  public void addTranslation(final TranslateObject translateObject, final NameEntry[] entries) {
    Set<TranslationText> translations = new HashSet<TranslationText>();

    if (translateObject.getTranslation() == null) {
      translateObject.setTranslation(new Translation());
    }

    for (int i = 0; i < entries.length; i++) {
      NameEntry entry = entries[i];

      TranslationText tt = new TranslationText();
      tt.setLocale(entry.getLocale());
      tt.setText(entry.getName());
      tt.setTranslation(translateObject.getTranslation());
      translations.add(tt);
    }

    translateObject.getTranslation().getTranslationTexts().clear();
    translateObject.getTranslation().getTranslationTexts().addAll(translations);
  }

  public String getText(final TranslateObject translateObject, final Locale locale) {
    Translation translation = translateObject.getTranslation();
    Set<TranslationText> translationTexts = translation.getTranslationTexts();

    String localeStr = locale.toString();
    for (TranslationText tt : translationTexts) {
      if (tt.getLocale().equals(localeStr)) {
        return tt.getText();
      }
    }

    return null;
  }

  public NameEntry[] getEmptyNameEntry(final MessageResources messages, final Locale locale) {
    NameEntry[] entriesL = new NameEntry[localeData.size()];

    int ix = 0;
    
    for (Map.Entry<String,String> localeEntry : localeData.entrySet()) {

      NameEntry entry = new NameEntry();
      entry.setLanguage(messages.getMessage(locale, localeEntry.getValue()));
      String loc = localeEntry.getKey();
      entry.setLocale(loc);

      entry.setName(null);

      entriesL[ix] = entry;
      ix++;
    }

    return entriesL;

  }

  public NameEntry[] getNameEntry(final MessageResources messages, final Locale locale,
      final TranslateObject translateObject) {
    NameEntry[] entriesL = new NameEntry[localeData.size()];

    Translation translation = translateObject.getTranslation();
    Set<TranslationText> translationTexts = translation.getTranslationTexts();

    Iterator<Map.Entry<String, String>> it = localeData.entrySet().iterator();
    int ix = 0;
    while (it.hasNext()) {
      Map.Entry<String, String> localeEntry = it.next();

      NameEntry entry = new NameEntry();
      entry.setLanguage(messages.getMessage(locale, localeEntry.getValue()));
      String loc = localeEntry.getKey();
      entry.setLocale(loc);

      for (TranslationText tt : translationTexts) {
        if (tt.getLocale().equals(loc)) {
          entry.setName(tt.getText());
          break;
        }
      }

      entriesL[ix] = entry;
      ix++;
    }

    return entriesL;

  }

  public static boolean isNameEntryBlankOrNull(final NameEntry[] entries) {
    if (entries == null) {
      return true;
    }

    for (int i = 0; i < entries.length; i++) {
      NameEntry entry = entries[i];
      if (GenericValidator.isBlankOrNull(entry.getName())) {
        return true;
      }
    }

    return false;
  }

}
