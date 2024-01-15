package ch.ess.hpadmin.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "homepage_language", catalog = "ess", uniqueConstraints = {@UniqueConstraint(columnNames = {"iso"})})
public class HomepageLanguage implements java.io.Serializable {

  // Fields    

  private Integer id;
  private String name;
  private Integer priority;
  private String iso;
  private Byte active;
  private Set<HomepageNewsText> homepageNewsTexts = new HashSet<HomepageNewsText>(0);
  private Set<HomepageFieldTextres> homepageFieldTextreses = new HashSet<HomepageFieldTextres>(0);

  // Constructors

  /** default constructor */
  public HomepageLanguage() {
  //no action
  }

  /** minimal constructor */
  public HomepageLanguage(Integer id, String name, Integer priority, String iso, Byte active) {
    this.id = id;
    this.name = name;
    this.priority = priority;
    this.iso = iso;
    this.active = active;
  }

  /** full constructor */
  public HomepageLanguage(Integer id, String name, Integer priority, String iso, Byte active,
      Set<HomepageNewsText> homepageNewsTexts, Set<HomepageFieldTextres> homepageFieldTextreses) {
    this.id = id;
    this.name = name;
    this.priority = priority;
    this.iso = iso;
    this.active = active;
    this.homepageNewsTexts = homepageNewsTexts;
    this.homepageFieldTextreses = homepageFieldTextreses;
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

  @Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "priority", unique = false, nullable = false, insertable = true, updatable = true)
  public Integer getPriority() {
    return this.priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  @Column(name = "iso", unique = true, nullable = false, insertable = true, updatable = true, length = 2)
  public String getIso() {
    return this.iso;
  }

  public void setIso(String iso) {
    this.iso = iso;
  }

  @Column(name = "active", unique = false, nullable = false, insertable = true, updatable = true)
  public Byte getActive() {
    return this.active;
  }

  public void setActive(Byte active) {
    this.active = active;
  }

  @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "homepageLanguage")
  public Set<HomepageNewsText> getHomepageNewsTexts() {
    return this.homepageNewsTexts;
  }

  public void setHomepageNewsTexts(Set<HomepageNewsText> homepageNewsTexts) {
    this.homepageNewsTexts = homepageNewsTexts;
  }

  @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "homepageLanguage")
  public Set<HomepageFieldTextres> getHomepageFieldTextreses() {
    return this.homepageFieldTextreses;
  }

  public void setHomepageFieldTextreses(Set<HomepageFieldTextres> homepageFieldTextreses) {
    this.homepageFieldTextreses = homepageFieldTextreses;
  }

}