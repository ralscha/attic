package ch.ess.sonne.server;

class Attachment {
  private String contenttype;
  private String filename;
  private byte[] content;

  public String getContenttype() {
    return contenttype;
  }

  public void setContenttype(String contenttype) {
    this.contenttype = contenttype;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }


}
