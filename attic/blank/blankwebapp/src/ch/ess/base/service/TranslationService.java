package ch.ess.base.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;

import ch.ess.base.Constants;
import ch.ess.base.model.TranslateObject;
import ch.ess.base.model.Translation;
import ch.ess.base.model.TranslationText;
import ch.ess.base.web.NameEntry;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/07/03 11:31:19 $ 
 * 
 * @spring.bean id="translationService" autowire="byName"
 */
public class TranslationService {

  private Map<String,String> localeData;

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
    Locale tmpLocale = checkLocale(locale);
    
    Translation translation = translateObject.getTranslation();
    Set<TranslationText> translationTexts = translation.getTranslationTexts();

    String localeStr = tmpLocale.toString();
    for (TranslationText tt : translationTexts) {
      if (tt.getLocale().equals(localeStr)) {
        return tt.getText();
      }
    }

    return null;
  }

  public NameEntry[] getEmptyNameEntry(final MessageResources messages, final Locale locale) {
    Locale tmpLocale = checkLocale(locale);
    
    NameEntry[] entriesL = new NameEntry[localeData.size()];

    int ix = 0;
    
    for (Map.Entry<String,String> localeEntry : localeData.entrySet()) {

      NameEntry entry = new NameEntry();
      entry.setLanguage(messages.getMessage(tmpLocale, localeEntry.getValue()));
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
    Locale tmpLocale = checkLocale(locale);
    
    NameEntry[] entriesL = new NameEntry[localeData.size()];

    Translation translation = translateObject.getTranslation();
    Set<TranslationText> translationTexts = translation.getTranslationTexts();

    Iterator<Map.Entry<String, String>> it = localeData.entrySet().iterator();
    int ix = 0;
    while (it.hasNext()) {
      Map.Entry<String, String> localeEntry = it.next();

      NameEntry entry = new NameEntry();
      entry.setLanguage(messages.getMessage(tmpLocale, localeEntry.getValue()));
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
      if (StringUtils.isBlank(entry.getName())) {
        return true;
      }
    }

    return false;
  }

  public Locale checkLocale(final Locale locale) {
    if (locale.equals(Constants.ENGLISH_LOCALE) || locale.equals(Constants.GERMAN_LOCALE)) {
      return locale;
    }
    
    Locale newLocale = new Locale(locale.getLanguage());
    if (newLocale.equals(Constants.ENGLISH_LOCALE) || newLocale.equals(Constants.GERMAN_LOCALE)) {
      return newLocale;
    }
    
    return Constants.ENGLISH_LOCALE;
  }
}
