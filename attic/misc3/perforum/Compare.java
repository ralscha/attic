
import java.util.*;

public class Compare {


	public static void main(String[] args) {

    List act = new ArrayList();

	  for (int i = 0; i < 10000; i++) {
	    Account a = new Account();
      a.setTotal(Math.random());
      act.add(a);
	  }
	  Comparator comp = new AccountComparator();

		long start = System.currentTimeMillis();
    Collections.sort(act, comp);

		System.out.println(System.currentTimeMillis() - start);


		act = new ArrayList();

		for (int i = 0; i < 10000; i++) {
		  AcountComparable a = new AcountComparable();
		  a.setTotal(Math.random());
		  act.add(a);
		}		

		start = System.currentTimeMillis();
		Collections.sort(act);

		System.out.println(System.currentTimeMillis() - start);

    GenericObjectSort gos = new GenericObjectSort();
    ICompareTool ict = new DoubleCompareTool();

		Account[] actArr = new Account[10000];

		for (int i = 0; i < 10000; i++) {
		  Account a = new Account();
		  a.setTotal(Math.random());
		  actArr[i] = a;
		}	

    start = System.currentTimeMillis();
    gos.sort(actArr, "total", new Class[0], new Object[0], ict);

    System.out.println(System.currentTimeMillis() - start);

	}
}