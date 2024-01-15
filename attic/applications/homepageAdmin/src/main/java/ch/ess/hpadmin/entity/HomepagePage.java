package ch.ess.hpadmin.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "homepage_page", catalog = "ess", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class HomepagePage implements java.io.Serializable {

  // Fields    

  private Integer id;
  private String name;
  private HomepageTemplate homepageTemplate;
  private HomepagePage homepagePage;
  private String title;
  private Short priority;
  private Short visibility;
  private Set<HomepagePage> homepagePages = new HashSet<HomepagePage>(0);

  // Constructors

  /** default constructor */
  public HomepagePage() {
  //no action
  }

  /** minimal constructor */
  public HomepagePage(Integer id, String name, Short priority, Short visibility) {
    this.id = id;
    this.name = name;
    this.priority = priority;
    this.visibility = visibility;
  }

  /** full constructor */
  public HomepagePage(Integer id, String name, HomepageTemplate homepageTemplate,
      HomepagePage homepagePage, String title, Short priority, Short visibility, Set<HomepagePage> homepagePages) {
    this.id = id;
    this.name = name;
    this.homepageTemplate = homepageTemplate;
    this.homepagePage = homepagePage;
    this.title = title;
    this.priority = priority;
    this.visibility = visibility;
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


  @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
  @JoinColumn(name = "template_id", unique = false, nullable = true, insertable = true, updatable = true)
  public HomepageTemplate getHomepageTemplate() {
    return this.homepageTemplate;
  }

  public void setHomepageTemplate(HomepageTemplate homepageTemplate) {
    this.homepageTemplate = homepageTemplate;
  }

  @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id", unique = false, nullable = true, insertable = true, updatable = true)
  public HomepagePage getHomepagePage() {
    return this.homepagePage;
  }

  public void setHomepagePage(HomepagePage homepagePage) {
    this.homepagePage = homepagePage;
  }

  @Column(name = "title", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Column(name = "priority", unique = false, nullable = false, insertable = true, updatable = true)
  public Short getPriority() {
    return this.priority;
  }

  public void setPriority(Short priority) {
    this.priority = priority;
  }

  @Column(name = "visibility", unique = false, nullable = false, insertable = true, updatable = true)
  public Short getVisibility() {
    return this.visibility;
  }

  public void setVisibility(Short visibility) {
    this.visibility = visibility;
  }

  @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "homepagePage")
  public Set<HomepagePage> getHomepagePages() {
    return this.homepagePages;
  }

  public void setHomepagePages(Set<HomepagePage> homepagePages) {
    this.homepagePages = homepagePages;
  }

  
  public String getName() {
    return name;
  }

  
  public void setName(String name) {
    this.name = name;
  }

}