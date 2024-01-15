package ch.ess.hpadmin.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "homepage_news_text", catalog = "ess", uniqueConstraints = {})
public class HomepageNewsText implements java.io.Serializable {

  // Fields    

  private HomepageNewsTextId id;
  private HomepageNews homepageNews;
  private HomepageLanguage homepageLanguage;
  private String text;

  // Constructors

  /** default constructor */
  public HomepageNewsText() {
  //no action
  }

  /** full constructor */
  public HomepageNewsText(HomepageNewsTextId id, HomepageNews homepageNews, HomepageLanguage homepageLanguage,
      String text) {
    this.id = id;
    this.homepageNews = homepageNews;
    this.homepageLanguage = homepageLanguage;
    this.text = text;
  }

  // Property accessors
  @EmbeddedId
  @AttributeOverrides({
      @AttributeOverride(name = "langId", column = @Column(name = "lang_id", unique = false, nullable = false, insertable = true, updatable = true)),
      @AttributeOverride(name = "newsId", column = @Column(name = "news_id", unique = false, nullable = false, insertable = true, updatable = true))})
  public HomepageNewsTextId getId() {
    return this.id;
  }

  public void setId(HomepageNewsTextId id) {
    this.id = id;
  }

  @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
  @JoinColumn(name = "news_id", unique = false, nullable = false, insertable = false, updatable = false)
  public HomepageNews getHomepageNews() {
    return this.homepageNews;
  }

  public void setHomepageNews(HomepageNews homepageNews) {
    this.homepageNews = homepageNews;
  }

  @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
  @JoinColumn(name = "lang_id", unique = false, nullable = false, insertable = false, updatable = false)
  public HomepageLanguage getHomepageLanguage() {
    return this.homepageLanguage;
  }

  public void setHomepageLanguage(HomepageLanguage homepageLanguage) {
    this.homepageLanguage = homepageLanguage;
  }

  @Column(name = "text", unique = false, nullable = false, insertable = true, updatable = true, length = 16777215)
  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

}