package ch.ess.speedsend;

import java.io.Serializable;

public class ProfileItem implements Serializable {
  private String name;
  private String host;
  private String localFile;
  private String remoteFileCompare;
  private String remoteFile;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getLocalFile() {
    return localFile;
  }

  public void setLocalFile(String localFile) {
    this.localFile = localFile;
  }

  public String getRemoteFile() {
    return remoteFile;
  }

  public void setRemoteFile(String remoteFile) {
    this.remoteFile = remoteFile;
  }

  public String getRemoteFileCompare() {
    return remoteFileCompare;
  }

  public void setRemoteFileCompare(String remoteFileCompare) {
    this.remoteFileCompare = remoteFileCompare;
  }

}
