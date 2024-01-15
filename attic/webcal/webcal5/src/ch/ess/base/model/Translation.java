package ch.ess.base.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

@Entity
public class Translation extends BaseObject {

  private Set<TranslationText> translationTexts = new HashSet<TranslationText>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "translation")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<TranslationText> getTranslationTexts() {
    return translationTexts;
  }

  public void setTranslationTexts(Set<TranslationText> translationTexts) {
    this.translationTexts = translationTexts;
  }

}
