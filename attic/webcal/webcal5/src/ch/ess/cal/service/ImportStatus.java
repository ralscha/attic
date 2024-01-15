package ch.ess.cal.service;

public class ImportStatus {
  private int noOfInserted;
  private int noOfUpdated;
  private int status;
  private String statusDescription;

  public int getNoOfInserted() {
    return noOfInserted;
  }

  public void setNoOfInserted(int noOfInserted) {
    this.noOfInserted = noOfInserted;
  }

  public int getNoOfUpdated() {
    return noOfUpdated;
  }

  public void setNoOfUpdated(int noOfUpdated) {
    this.noOfUpdated = noOfUpdated;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getStatusDescription() {
    return statusDescription;
  }

  public void setStatusDescription(String statusDescription) {
    this.statusDescription = statusDescription;
  }

}
