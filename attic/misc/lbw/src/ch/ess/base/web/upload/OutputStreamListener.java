package ch.ess.base.web.upload;


public interface OutputStreamListener {
  public void start();

  public void bytesRead(int bytesRead);

  public void error(String message);

  public void done();
}
