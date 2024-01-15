package ch.ess.tt.service;

import java.io.InputStream;

public class Attachment {

  private String mimeType;
  private InputStream attachmentInputStream;

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public InputStream getAttachmentInputStream() {
    return attachmentInputStream;
  }

  public void setAttachmentInputStream(InputStream attachmentInputStream) {
    this.attachmentInputStream = attachmentInputStream;
  }

}
