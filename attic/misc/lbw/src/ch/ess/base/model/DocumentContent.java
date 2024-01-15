package ch.ess.base.model;

import java.sql.Blob;

import javax.persistence.Entity;

@Entity
public class DocumentContent extends BaseObject {
  private Blob content;
  
  public Blob getContent() {
    return content;
  }

  public void setContent(Blob content) {
    this.content = content;
  }


  
  
}