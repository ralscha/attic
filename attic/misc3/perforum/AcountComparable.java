
import java.util.*;

public class AcountComparable implements Comparable {

  private double total;

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public int compareTo(Object o) {
    AcountComparable a = (AcountComparable)o;
    if (this.getTotal() < a.getTotal())
      return -1;
    else if (this.getTotal() > a.getTotal())
      return 1;
    else
      return 0;
  }

}