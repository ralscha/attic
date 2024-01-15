package ch.ess.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TranslationText extends BaseObject {

  private String locale;
  private String text;
  private Translation translation;

  @ManyToOne
  @JoinColumn(name = "translationId", nullable = false)
  public Translation getTranslation() {
    return translation;
  }

  public void setTranslation(Translation translation) {
    this.translation = translation;
  }

  @Column(length = 10, nullable = false)
  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  @Column(length = 4000, nullable = false)
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
