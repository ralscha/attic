package ch.ess.downloader.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name="dl_file")
public class File extends BaseObject {

  private Folder folder;
  private String fileName;
  private String physicalFileName;
  private String checksumString;
  private Integer sizeInBytes;
  private Date createDate;
  private Date accessedDate;
  private Date downloadStart;
  private Date downloadEnd;
  private String mimeType;
  private String uuid;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "folderId")
  @NotNull
  public Folder getFolder() {
    return this.folder;
  }

  public void setFolder(Folder folder) {
    this.folder = folder;
  }

  @Length(max=255)
  public String getFileName() {
    return this.fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Length(max=255)
  public String getPhysicalFileName() {
    return this.physicalFileName;
  }

  public void setPhysicalFileName(String physicalFileName) {
    this.physicalFileName = physicalFileName;
  }

  @Length(max=50)
  public String getChecksumString() {
    return this.checksumString;
  }

  public void setChecksumString(String checksum) {
    this.checksumString = checksum;
  }

  public Integer getSizeInBytes() {
    return this.sizeInBytes;
  }

  public void setSizeInBytes(Integer sizeInBytes) {
    this.sizeInBytes = sizeInBytes;
  }

  @Temporal(TemporalType.TIMESTAMP)
  public Date getCreateDate() {
    return this.createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Temporal(TemporalType.TIMESTAMP)
  public Date getAccessedDate() {
    return this.accessedDate;
  }

  public void setAccessedDate(Date accessedDate) {
    this.accessedDate = accessedDate;
  }

  @Length(max=100)
  public String getMimeType() {
    return this.mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  @Length(max=50)
  public String getUuid() {
    return uuid;
  }
  
  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  @Temporal(TemporalType.TIMESTAMP)
  public Date getDownloadStart() {
    return downloadStart;
  }

  
  public void setDownloadStart(Date downloadStart) {
    this.downloadStart = downloadStart;
  }

  @Temporal(TemporalType.TIMESTAMP)
  public Date getDownloadEnd() {
    return downloadEnd;
  }

  
  public void setDownloadEnd(Date downloadEnd) {
    this.downloadEnd = downloadEnd;
  }

}
