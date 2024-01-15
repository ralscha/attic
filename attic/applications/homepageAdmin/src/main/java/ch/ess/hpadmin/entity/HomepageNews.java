package ch.ess.hpadmin.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "homepage_news", catalog = "ess", uniqueConstraints = {})
public class HomepageNews implements java.io.Serializable {

  // Fields    

  private Integer id;
  private Date date;
  private String author;
  private Set<HomepageNewsText> homepageNewsTexts = new HashSet<HomepageNewsText>(0);

  // Constructors

  /** default constructor */
  public HomepageNews() {
  //no action
  }

  /** minimal constructor */
  public HomepageNews(Integer id, Date date, String author) {
    this.id = id;
    this.date = date;
    this.author = author;
  }

  /** full constructor */
  public HomepageNews(Integer id, Date date, String author, Set<HomepageNewsText> homepageNewsTexts) {
    this.id = id;
    this.date = date;
    this.author = author;
    this.homepageNewsTexts = homepageNewsTexts;
  }

  // Property accessors
  @Id
  @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true)
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Temporal(TemporalType.DATE)
  @Column(name = "date", unique = false, nullable = false, insertable = true, updatable = true, length = 0)
  public Date getDate() {
    return this.date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Column(name = "author", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "homepageNews")
  public Set<HomepageNewsText> getHomepageNewsTexts() {
    return this.homepageNewsTexts;
  }

  public void setHomepageNewsTexts(Set<HomepageNewsText> homepageNewsTexts) {
    this.homepageNewsTexts = homepageNewsTexts;
  }

}