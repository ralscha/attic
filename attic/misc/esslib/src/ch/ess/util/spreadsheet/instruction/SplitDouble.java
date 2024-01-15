

package ch.ess.util.spreadsheet.instruction;

public class SplitDouble {

  private int sPart;
  private long iPart;
  private double fPart;


  public SplitDouble(double param) {

    super();

    if (param < 0) {
      sPart = -1;
      param = -param;
    } else {
      sPart = 1;
    }

    iPart = Math.round(Math.floor(param));
    fPart = param - iPart;
  }
  
  public double getFPart() {
    return fPart;
  }

  public long getIPart() {
    return iPart;
  }

  public int getSPart() {
    return sPart;
  }

}
