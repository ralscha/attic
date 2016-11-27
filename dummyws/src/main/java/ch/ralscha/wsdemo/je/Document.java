package ch.ralscha.wsdemo.je;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class Document {

  @PrimaryKey(sequence = "document_sequence")
  private long id;

  private String fileKey;
  private String fileName;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFileKey() {
    return fileKey;
  }

  public void setFileKey(String fileKey) {
    this.fileKey = fileKey;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

}
