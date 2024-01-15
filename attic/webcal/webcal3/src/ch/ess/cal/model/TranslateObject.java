package ch.ess.cal.model;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author sr
 */
public class TranslateObject extends BaseObject {

  private Translation translation;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "translationId", nullable = false, unique = true)
  public Translation getTranslation() {
    return translation;
  }

  public void setTranslation(Translation translation) {
    this.translation = translation;
  }

}
