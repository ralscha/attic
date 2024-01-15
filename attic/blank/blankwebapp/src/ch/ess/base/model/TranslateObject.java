package ch.ess.base.model;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddableSuperclass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author sr
 */
@EmbeddableSuperclass
public class TranslateObject extends BaseObject {

  protected Translation translation;

  @ManyToOne(cascade = CascadeType.ALL)  
  @JoinColumn(name = "translationId", nullable = false, unique = true)
  public Translation getTranslation() {
    return translation;
  }

  public void setTranslation(Translation translation) {
    this.translation = translation;
  }

}
