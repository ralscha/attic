package ch.ess.base.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import ch.ess.base.Util;
import ch.ess.lbw.model.UserWerk;

@Entity
public class User extends BaseObject {

  private String name;
  private String firstName;
  private String userName;
  private String passwordHash;
  private String loginToken;
  private Date loginTokenTime;
  private String email;
  
  private Boolean kriteriumPreis;
  private Boolean kriteriumService;
  private Boolean kriteriumTeilequalitaet;
  private Boolean kriteriumSap;

  private Locale locale;

  private Set<UserWerk> userWerke = new HashSet<UserWerk>();
  private Set<UserUserGroup> userUserGroups = new HashSet<UserUserGroup>();
  private Set<UserConfiguration> userConfiguration = new HashSet<UserConfiguration>();

  @Column(nullable = false, length = 10)
  public Locale getLocale() {
    return locale;
  }

  @Column(nullable = false, length = 100)
  public String getName() {
    return name;
  }

  @Column(nullable = false, length = 100)
  public String getFirstName() {
    return firstName;
  }

  @Column(length = 40)
  public String getPasswordHash() {
    return passwordHash;
  }

  @Column(nullable = false, length = 100, unique = true)
  public String getUserName() {
    return userName;
  }

  @Column(length = 40)
  public String getLoginToken() {
    return loginToken;
  }

  @Type(type = "timestamp")
  public Date getLoginTokenTime() {
    return Util.clone(loginTokenTime);
  }

  public void setLoginToken(final String string) {
    loginToken = string;
  }

  public void setLoginTokenTime(final Date date) {
    loginTokenTime = Util.clone(date);
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLocale(final Locale locale) {
    this.locale = locale;
  }

  public void setName(final String string) {
    name = string;
  }

  public void setPasswordHash(final String string) {
    passwordHash = string;
  }

  public void setUserName(final String string) {
    userName = string;
  }

  @Column(nullable = false, length = 255)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<UserConfiguration> getUserConfiguration() {
    return userConfiguration;
  }

  public void setUserConfiguration(Set<UserConfiguration> userConfiguration) {
    this.userConfiguration = userConfiguration;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<UserUserGroup> getUserUserGroups() {
    return userUserGroups;
  }

  public void setUserUserGroups(Set<UserUserGroup> userUserGroups) {
    this.userUserGroups = userUserGroups;
  }

  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<UserWerk> getUserWerke() {
    return userWerke;
  }

  public void setUserWerke(Set<UserWerk> userWerke) {
    this.userWerke = userWerke;
  }

  public Boolean getKriteriumPreis() {
    return kriteriumPreis;
  }

  public void setKriteriumPreis(Boolean kriteriumPreis) {
    this.kriteriumPreis = kriteriumPreis;
  }

  public Boolean getKriteriumSap() {
    return kriteriumSap;
  }

  public void setKriteriumSap(Boolean kriteriumSap) {
    this.kriteriumSap = kriteriumSap;
  }

  public Boolean getKriteriumService() {
    return kriteriumService;
  }

  public void setKriteriumService(Boolean kriteriumService) {
    this.kriteriumService = kriteriumService;
  }

  public Boolean getKriteriumTeilequalitaet() {
    return kriteriumTeilequalitaet;
  }

  public void setKriteriumTeilequalitaet(Boolean kriteriumTeilequalitaet) {
    this.kriteriumTeilequalitaet = kriteriumTeilequalitaet;
  }

}