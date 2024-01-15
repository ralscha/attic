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
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "homepage_field", catalog = "ess", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class HomepageField implements java.io.Serializable {

  // Fields    

  private Integer id;
  private String name;
  private Set<HomepageFieldTextres> homepageFieldTextreses = new HashSet<HomepageFieldTextres>(0);

  // Constructors

  /** default constructor */
  public HomepageField() {
  //no action
  }

  /** minimal constructor */
  public HomepageField(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  /** full constructor */
  public HomepageField(Integer id, String name, Set<HomepageFieldTextres> homepageFieldTextreses) {
    this.id = id;
    this.name = name;
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

  @Column(name = "name", unique = true, nullable = false, insertable = true, updatable = true, length = 128)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "homepageField")
  public Set<HomepageFieldTextres> getHomepageFieldTextreses() {
    return this.homepageFieldTextreses;
  }

  public void setHomepageFieldTextreses(Set<HomepageFieldTextres> homepageFieldTextreses) {
    this.homepageFieldTextreses = homepageFieldTextreses;
  }


}