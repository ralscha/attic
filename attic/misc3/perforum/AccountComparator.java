
import java.util.*;

public class AccountComparator implements Comparator {

  public int compare(Object o1, Object o2) {
    Account a1 = (Account)o1;
    Account a2 = (Account)o2;
    
    if (a1.getTotal() < a2.getTotal())
      return -1;
    else if (a1.getTotal() > a2.getTotal())
      return 1;
    else
      return 0;
  }

}