package ch.ess.sonne.client.common;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ImageInfo implements IsSerializable {
  private String fileName;
  private String dateTime;

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

}
