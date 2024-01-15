package ch.ess.cal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import ch.ess.base.model.BaseObject;

@Entity
public class Directory extends BaseObject {

  private Directory parent;
  private String name;
  private String description;  
  private Set<File> files = new HashSet<File>();
  private Set<Directory> children = new HashSet<Directory>();

  private Set<DirectoryPermission> directoryPermission = new HashSet<DirectoryPermission>();
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<Directory> getChildren() {
    return children;
  }

  public void setChildren(Set<Directory> children) {
    this.children = children;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "directory")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<File> getFiles() {
    return files;
  }

  public void setFiles(Set<File> files) {
    this.files = files;
  }

  @Column(length = 255)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  @Column(length = 1000)
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @ManyToOne
  @JoinColumn(name = "parentDirectoryId", nullable = true)
  public Directory getParent() {
    return parent;
  }

  public void setParent(Directory parent) {
    this.parent = parent;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "directory")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)    
  public Set<DirectoryPermission> getDirectoryPermission() {
    return directoryPermission;
  }

  public void setDirectoryPermission(Set<DirectoryPermission> directoryPermission) {
    this.directoryPermission = directoryPermission;
  }
  
  
}
