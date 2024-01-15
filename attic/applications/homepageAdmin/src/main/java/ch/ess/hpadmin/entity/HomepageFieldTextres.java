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
@Table(name = "homepage_field_textres", catalog = "ess", uniqueConstraints = {})
public class HomepageFieldTextres implements java.io.Serializable {

  // Fields    

  private HomepageFieldTextresId id;
  private HomepageTextResource homepageTextResource;
  private HomepageLanguage homepageLanguage;
  private HomepageField homepageField;

  // Constructors

  /** default constructor */
  public HomepageFieldTextres() {
  //no action
  }

  /** full constructor */
  public HomepageFieldTextres(HomepageFieldTextresId id, HomepageTextResource homepageTextResource,
      HomepageLanguage homepageLanguage, HomepageField homepageField) {
    this.id = id;
    this.homepageTextResource = homepageTextResource;
    this.homepageLanguage = homepageLanguage;
    this.homepageField = homepageField;
  }

  // Property accessors
  @EmbeddedId
  @AttributeOverrides({
      @AttributeOverride(name = "fieldId", column = @Column(name = "field_id", unique = false, nullable = false, insertable = true, updatable = true)),
      @AttributeOverride(name = "langId", column = @Column(name = "lang_id", unique = false, nullable = false, insertable = true, updatable = true))})
  public HomepageFieldTextresId getId() {
    return this.id;
  }

  public void setId(HomepageFieldTextresId id) {
    this.id = id;
  }

  @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
  @JoinColumn(name = "textres_id", unique = false, nullable = false, insertable = true, updatable = true)
  public HomepageTextResource getHomepageTextResource() {
    return this.homepageTextResource;
  }

  public void setHomepageTextResource(HomepageTextResource homepageTextResource) {
    this.homepageTextResource = homepageTextResource;
  }

  @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
  @JoinColumn(name = "lang_id", unique = false, nullable = false, insertable = false, updatable = false)
  public HomepageLanguage getHomepageLanguage() {
    return this.homepageLanguage;
  }

  public void setHomepageLanguage(HomepageLanguage homepageLanguage) {
    this.homepageLanguage = homepageLanguage;
  }

  @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
  @JoinColumn(name = "field_id", unique = false, nullable = false, insertable = false, updatable = false)
  public HomepageField getHomepageField() {
    return this.homepageField;
  }

  public void setHomepageField(HomepageField homepageField) {
    this.homepageField = homepageField;
  }

}