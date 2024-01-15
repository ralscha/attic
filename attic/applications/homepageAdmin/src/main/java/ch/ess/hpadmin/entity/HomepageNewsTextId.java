package ch.ess.hpadmin.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class HomepageNewsTextId implements java.io.Serializable {

  // Fields    

  private Integer langId;
  private Integer newsId;

  // Constructors

  /** default constructor */
  public HomepageNewsTextId() {
  //no action
  }

  /** full constructor */
  public HomepageNewsTextId(Integer langId, Integer newsId) {
    this.langId = langId;
    this.newsId = newsId;
  }

  // Property accessors

  @Column(name = "lang_id", unique = false, nullable = false, insertable = true, updatable = true)
  public Integer getLangId() {
    return this.langId;
  }

  public void setLangId(Integer langId) {
    this.langId = langId;
  }

  @Column(name = "news_id", unique = false, nullable = false, insertable = true, updatable = true)
  public Integer getNewsId() {
    return this.newsId;
  }

  public void setNewsId(Integer newsId) {
    this.newsId = newsId;
  }

  @Override
  public boolean equals(Object other) {
    if ((this == other))
      return true;
    if ((other == null))
      return false;
    if (!(other instanceof HomepageNewsTextId))
      return false;
    HomepageNewsTextId castOther = (HomepageNewsTextId)other;

    return ((this.getLangId() == castOther.getLangId()) || (this.getLangId() != null && castOther.getLangId() != null && this
        .getLangId().equals(castOther.getLangId())))
        && ((this.getNewsId() == castOther.getNewsId()) || (this.getNewsId() != null && castOther.getNewsId() != null && this
            .getNewsId().equals(castOther.getNewsId())));
  }

  @Override
  public int hashCode() {
    int result = 17;

    result = 37 * result + (getLangId() == null ? 0 : this.getLangId().hashCode());
    result = 37 * result + (getNewsId() == null ? 0 : this.getNewsId().hashCode());
    return result;
  }

}