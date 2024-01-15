package ch.ess.base.service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;

import ch.ess.base.model.TranslateObject;
import ch.ess.base.model.Translation;
import ch.ess.base.model.TranslationText;
import ch.ess.base.web.NameEntry;
import ch.ess.base.xml.locale.Locales;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;


@SpringBean(id = "translationService", autowire = Autowire.BYTYPE)
public class TranslationService {

  private Locales locales;

  public void setLocales(Locales locales) {
    this.locales = locales;
  }

  public void addTranslation(final TranslateObject translateObject, final NameEntry[] entries) {
    Set<TranslationText> translations = new HashSet<TranslationText>();

    if (translateObject.getTranslation() == null) {
      translateObject.setTranslation(new Translation());
    }

    for (NameEntry entry : entries) {
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
    
    NameEntry[] entriesL = new NameEntry[locales.size()];

    int ix = 0;
    
    for (ch.ess.base.xml.locale.Locale xmlLocale : locales.getLocales()) {
      NameEntry entry = new NameEntry();
      entry.setLanguage(xmlLocale.getText().get(tmpLocale));
      entry.setLocale(xmlLocale.getKey());
      entry.setName(null);

      entriesL[ix] = entry;
      ix++;            
    }
    
    return entriesL;

  }

  public NameEntry[] getNameEntry(final MessageResources messages, final Locale locale,
      final TranslateObject translateObject) {
    Locale tmpLocale = checkLocale(locale);
    
    NameEntry[] entriesL = new NameEntry[locales.size()];

    Translation translation = translateObject.getTranslation();
    Set<TranslationText> translationTexts = translation.getTranslationTexts();

    int ix = 0;
    for (ch.ess.base.xml.locale.Locale xmlLocale : locales.getLocales()) {
      NameEntry entry = new NameEntry();
      entry.setLanguage(xmlLocale.getText().get(tmpLocale));
      String loc = xmlLocale.getKey();
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

    for (NameEntry entry : entries) {
      if (StringUtils.isBlank(entry.getName())) {
        return true;
      }
    }

    return false;
  }

  public Locale checkLocale(final Locale locale) {
    
    for (ch.ess.base.xml.locale.Locale xmlLocale : locales.getLocales()) {
      if (locale.equals(xmlLocale.getLocale())) {
        return locale;
      }
    }
    
    Locale newLocale = new Locale(locale.getLanguage());
    
    for (ch.ess.base.xml.locale.Locale xmlLocale : locales.getLocales()) {
      if (newLocale.equals(xmlLocale.getLocale())) {
        return newLocale;
      }
    }    
    
    for (ch.ess.base.xml.locale.Locale xmlLocale : locales.getLocales()) {
      if (xmlLocale.isDefaultLocale()) {
        return xmlLocale.getLocale();
      }
    } 
    
    return null;

  }
}
