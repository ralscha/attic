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
import ch.ess.base.model.Document;

@Entity
public class File extends BaseObject {
  private String name;
  private Directory directory;
  private Document document;
  private String description;  
  private Set<FilePermission> filePermission = new HashSet<FilePermission>();

  @ManyToOne
  @JoinColumn(name = "directoryId", nullable = false)
  public Directory getDirectory() {
    return directory;
  }

  public void setDirectory(Directory directory) {
    this.directory = directory;
  }

  @Column(length = 255)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Document getDocument() {
    return document;
  }

  public void setDocument(Document document) {
    this.document = document;
  }

  @Column(length = 1000)
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "file")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)  
  public Set<FilePermission> getFilePermission() {
    return filePermission;
  }

  public void setFilePermission(Set<FilePermission> filePermission) {
    this.filePermission = filePermission;
  }


}
