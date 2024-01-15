package ch.ess.ex4u.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import ch.ess.ex4u.shared.PrincipalDetail;
import ch.ess.sgwt.annotations.Detail;
import ch.ess.sgwt.annotations.EditorType;
import ch.ess.sgwt.annotations.Hidden;
import ch.ess.sgwt.annotations.Title;
import ch.ess.sgwt.annotations.ValueMap;
import ch.ess.sgwt.annotations.ValueMap.Value;

@Entity
@Table(name = "`User`")
public class User extends AbstractEntity {

  public User() {
    super();
  }

  public User(PrincipalDetail pd) {
    super();
    setId(pd.getId());
    setName(pd.getName());
    String[] split = pd.getFullName().split(" ");
    setFirstName(split[0]);
    setName(split[1]);
    setEmail(split[2]);
  }

  public PrincipalDetail getPrinzipalDetail() {
    PrincipalDetail pd = new PrincipalDetail();
    pd.setId(getId());
    pd.setName(getUserName());
    pd.setFullName(getFirstName() + " " + getName() + " " + getEmail());
    return pd;
  }

  @NotNull
  @Length(max = 100)
  @Column(unique = true)
  private String userName;

  @Title("Name")
  @Length(max = 200)
  private String name;

  @Title("First Name")
  @Length(max = 200)
  private String firstName;

  @Title("Date of Birth")
  private Date dateOfBirth;

  @Title("E-Mail Address")
  @Email
  @Length(max = 255)
  @NotNull
  private String email;

  @Hidden
  @Length(max = 40)
  private String passwordHash;

  @Length(max = 255)
  private String openId;

  @Length(max = 8)
  @ValueMap({@Value(ID = "en_US", text = "Englisch"), @Value(ID = "de_CH", text = "Deutsch"), @Value(ID = "fr_CH", text = "Französisch")})
  @EditorType("SelectOtherItem")
  private String locale;

  @Title("Enabled")
  @Detail(true)
  private boolean enabled;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "UserRoles", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
  private Set<Role> roles;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "emas")
  private Set<Vertrag> vertraege;

  @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
  @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
      org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
  private Set<Zeit> zeiten = new HashSet<Zeit>();

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void addRole(Role role) {
    if (this.roles == null)
      this.roles = new HashSet<Role>();
    this.roles.add(role);
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public Set<Zeit> getZeiten() {
    return zeiten;
  }

  public void setZeiten(Set<Zeit> zeiten) {
    this.zeiten = zeiten;
  }

  public void addZeit(Zeit zeit) {
    zeit.setUser(this);
    this.zeiten.add(zeit);
  }

  public Set<Vertrag> getVertraege() {
    return vertraege;
  }

  public void setVertraege(Set<Vertrag> vertraege) {
    this.vertraege = vertraege;
  }

  public void addVertrag(Vertrag vertrag) {
    if (this.vertraege == null)
      this.vertraege = new HashSet<Vertrag>();
    this.vertraege.add(vertrag);
  }
}
