package ch.ess.base.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.upload.FormFile;
import org.hibernate.lob.BlobImpl;

@Embeddable
public class Document implements Serializable {
  private DocumentContent content;
  private Integer contentHash;
  private Integer contentSize;
  private String contentType;
  private String fileName;

  public Document() {
    //default constructor  
  }

  public Document(FormFile file) throws FileNotFoundException, IOException {

    //Compress
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    
    GZIPOutputStream gzipOutStream = new GZIPOutputStream(bos);
    IOUtils.copy(file.getInputStream(), gzipOutStream);
    gzipOutStream.close();    
    
    InputStream inputStream = file.getInputStream();
    this.contentHash = computeContentHashCode(inputStream);
    inputStream.close();

    this.contentType = file.getContentType();
    this.fileName = file.getFileName();
    this.contentSize = file.getFileSize();
    DocumentContent cont = new DocumentContent();
    
    cont.setContent(new BlobImpl(bos.toByteArray()));    
    setContent(cont);
  }



  public void writeContent(OutputStream out) throws IOException, SQLException {
    GZIPInputStream gzipInputStream = new GZIPInputStream(content.getContent().getBinaryStream());
    IOUtils.copy(gzipInputStream, out);
    gzipInputStream.close();
  }

  @Transient
  public String getFileExtension() {
    int dotPos = fileName.lastIndexOf('.'); 
    if (dotPos >= 0) {
      return fileName.substring(dotPos+1);
    }
    return null;
  }
  
  @Override
  public boolean equals(Object o) {
    if (o != null) {
      Document d = (Document)o;

      EqualsBuilder equalsBuilder = new EqualsBuilder();
      equalsBuilder.append(contentHash, d.getContentHash());
      equalsBuilder.append(contentType, d.getContentType());
      equalsBuilder.append(fileName, d.getFileName());
      equalsBuilder.append(contentSize, d.getContentSize());
      
      return equalsBuilder.isEquals();
    }
    return false;
  }

  @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL, optional=true)
  @JoinColumn(name="documentContentId")  
  public DocumentContent getContent() {
    return content;
  }

  public void setContent(DocumentContent content) {
    this.content = content;
  }

  public Integer getContentHash() {
    return contentHash;
  }

  public void setContentHash(Integer contentHash) {
    this.contentHash = contentHash;
  }

  public Integer getContentSize() {
    return contentSize;
  }

  public void setContentSize(Integer contentSize) {
    this.contentSize = contentSize;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
    hashCodeBuilder.append(contentHash);
    hashCodeBuilder.append(contentType);
    hashCodeBuilder.append(fileName);
    hashCodeBuilder.append(contentSize);
    return hashCodeBuilder.hashCode();
  }

  private static int computeContentHashCode(InputStream theContent) throws IOException {
    BufferedInputStream stream = new BufferedInputStream(theContent);

    HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();

    byte[] b = new byte[1];
    while (stream.read(b) == 1) {
      hashCodeBuilder.append(b);
    }

    return hashCodeBuilder.toHashCode();
  }



  @Override
  public String toString() {
    ToStringBuilder stringBuilder = new ToStringBuilder(ToStringStyle.MULTI_LINE_STYLE);
    stringBuilder.append(contentHash);
    stringBuilder.append(contentType);
    stringBuilder.append(fileName);
    stringBuilder.append(contentSize);
    return stringBuilder.toString();
  }

  
  
}
