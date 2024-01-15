import java.util.*;
import java.text.*;

public class Tests7 {

	public static void main(String[] args) {
		
		dateTest();
		/*int split = 7;
		
		List l = new ArrayList();
		for (int i = 0; i < 15; i++) {
			l.add(""+i);
		}
		
		int x = 0;
		while(l.size() > 0) {
			List sl = l.subList(0, l.size() > split ? split : l.size());
			//System.out.println((x++)+" "+sl.size());
			Iterator it = sl.iterator();
			while(it.hasNext()) 
				System.out.println(it.next());
			sl.clear();
		}
		//System.out.println(l.size());	
		*/
		
	}
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	private static void dateTest() {
	
		Calendar anyDay = new GregorianCalendar(1999, Calendar.FEBRUARY, 23);
		test(anyDay);
		anyDay = new GregorianCalendar(1999, Calendar.OCTOBER, 13);
		test(anyDay);
		anyDay = new GregorianCalendar(2000, Calendar.JANUARY, 30);
		test(anyDay);	
	}
	
	private static void test(Calendar cal) {
		if (isToday(cal)) {
			System.out.println("HEUTE "+sdf.format(cal.getTime()));
		} else {
			System.out.println("NE du "+sdf.format(cal.getTime()));
		}
	}
	
	private static boolean isToday(Calendar anyDay) {
		Calendar today = Calendar.getInstance();
		return ((today.get(Calendar.DATE) == anyDay.get(Calendar.DATE)) &&
		    (today.get(Calendar.MONTH) == anyDay.get(Calendar.MONTH)) &&
		    (today.get(Calendar.YEAR) == anyDay.get(Calendar.YEAR)));
	
	}
}