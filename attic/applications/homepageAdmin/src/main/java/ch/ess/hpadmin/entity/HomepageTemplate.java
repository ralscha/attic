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
@Table(name = "homepage_template", catalog = "ess", uniqueConstraints = {})
public class HomepageTemplate implements java.io.Serializable {

  // Fields    

  private Integer id;
  private String tplMain;
  private String tplRight;
  private Set<HomepagePage> homepagePages = new HashSet<HomepagePage>(0);

  // Constructors

  /** default constructor */
  public HomepageTemplate() {
  //no action
  }

  /** minimal constructor */
  public HomepageTemplate(Integer id, String tplMain) {
    this.id = id;
    this.tplMain = tplMain;
  }

  /** full constructor */
  public HomepageTemplate(Integer id, String tplMain, String tplRight, Set<HomepagePage> homepagePages) {
    this.id = id;
    this.tplMain = tplMain;
    this.tplRight = tplRight;
    this.homepagePages = homepagePages;
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

  @Column(name = "tpl_main", unique = false, nullable = false, insertable = true, updatable = true, length = 45)
  public String getTplMain() {
    return this.tplMain;
  }

  public void setTplMain(String tplMain) {
    this.tplMain = tplMain;
  }

  @Column(name = "tpl_right", unique = false, nullable = true, insertable = true, updatable = true, length = 45)
  public String getTplRight() {
    return this.tplRight;
  }

  public void setTplRight(String tplRight) {
    this.tplRight = tplRight;
  }

  @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "homepageTemplate")
  public Set<HomepagePage> getHomepagePages() {
    return this.homepagePages;
  }

  public void setHomepagePages(Set<HomepagePage> homepagePages) {
    this.homepagePages = homepagePages;
  }

}