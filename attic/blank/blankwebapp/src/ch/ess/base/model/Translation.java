package ch.ess.base.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

/** A business entity class representing a Translation
 * 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:19 $
 */

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
