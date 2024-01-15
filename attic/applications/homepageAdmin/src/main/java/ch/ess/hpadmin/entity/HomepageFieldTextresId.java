package ch.ess.hpadmin.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class HomepageFieldTextresId implements java.io.Serializable {

  // Fields    

  private Integer fieldId;
  private Integer langId;

  // Constructors

  /** default constructor */
  public HomepageFieldTextresId() {
    //no action
  }

  /** full constructor */
  public HomepageFieldTextresId(Integer fieldId, Integer langId) {
    this.fieldId = fieldId;
    this.langId = langId;
  }

  // Property accessors

  @Column(name = "field_id", unique = false, nullable = false, insertable = true, updatable = true)
  public Integer getFieldId() {
    return this.fieldId;
  }

  public void setFieldId(Integer fieldId) {
    this.fieldId = fieldId;
  }

  @Column(name = "lang_id", unique = false, nullable = false, insertable = true, updatable = true)
  public Integer getLangId() {
    return this.langId;
  }

  public void setLangId(Integer langId) {
    this.langId = langId;
  }

  @Override
  public boolean equals(Object other) {
    if ((this == other))
      return true;
    if ((other == null))
      return false;
    if (!(other instanceof HomepageFieldTextresId))
      return false;
    HomepageFieldTextresId castOther = (HomepageFieldTextresId)other;

    return ((this.getFieldId() == castOther.getFieldId()) || (this.getFieldId() != null
        && castOther.getFieldId() != null && this.getFieldId().equals(castOther.getFieldId())))
        && ((this.getLangId() == castOther.getLangId()) || (this.getLangId() != null && castOther.getLangId() != null && this
            .getLangId().equals(castOther.getLangId())));
  }

  @Override
  public int hashCode() {
    int result = 17;

    result = 37 * result + (getFieldId() == null ? 0 : this.getFieldId().hashCode());
    result = 37 * result + (getLangId() == null ? 0 : this.getLangId().hashCode());
    return result;
  }

}