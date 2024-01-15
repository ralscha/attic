package ch.ess.hpadmin.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "homepage_text_resource", catalog = "ess", uniqueConstraints = {})
public class HomepageTextResource implements java.io.Serializable {

  // Fields    

  private Integer id;
  private String text;
  private Set<HomepageFieldTextres> homepageFieldTextreses = new HashSet<HomepageFieldTextres>(0);

  // Constructors

  /** default constructor */
  public HomepageTextResource() {
  //no action
  }

  /** minimal constructor */
  public HomepageTextResource(Integer id, String text) {
    this.id = id;
    this.text = text;
  }

  /** full constructor */
  public HomepageTextResource(Integer id, String text, Set<HomepageFieldTextres> homepageFieldTextreses) {
    this.id = id;
    this.text = text;
    this.homepageFieldTextreses = homepageFieldTextreses;
  }

  // Property accessors
  @Id
  @GeneratedValue
  @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true)
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "text", unique = false, nullable = false, insertable = true, updatable = true, length = 16777215)
  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "homepageTextResource")
  public Set<HomepageFieldTextres> getHomepageFieldTextreses() {
    return this.homepageFieldTextreses;
  }

  public void setHomepageFieldTextreses(Set<HomepageFieldTextres> homepageFieldTextreses) {
    this.homepageFieldTextreses = homepageFieldTextreses;
  }

}