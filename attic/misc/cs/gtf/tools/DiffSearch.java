package gtf.tools;

import java.io.*;
import java.util.*;
import com.tce.math.TBigDecimal;

public class DiffSearch {
	static {
		COM.stevesoft.pat.Key.registeredTo("Gamma.Pleides/-407061011");
	} 

	private boolean checkTotal(List l) {
		TBigDecimal total = TBigDecimal.ZERO;
		TBigDecimal zero = TBigDecimal.ZERO;

		for (int i = 0; i < l.size(); i++) {
			DiffSearchBooking dsb = (DiffSearchBooking)l.get(i);
			total = total.add(dsb.getAmount());
		} 

		if (total.compareTo(zero) == 0) {
			return false;
		} else {
			return true;
		} 
	} 

	public static void main(String args[]) {
		if (args.length == 1) {
			new DiffSearch().search(args[0]);
		} else {
			System.out.println("DiffSearch <fileName>");
		} 
	} 

	private void printList(List l) {
		for (int i = 0; i < l.size(); i++) {
			DiffSearchBooking dsb = (DiffSearchBooking)l.get(i);
			System.out.println(dsb);
		} 
	} 

	public void search(String fileName) {
		BufferedReader dis;
		String line;
		TBigDecimal mamount = null;
		List bookings = new ArrayList();

		try {
			dis = new BufferedReader(new FileReader(fileName));
			line = dis.readLine();

			while (line != null) {
				DiffSearchBooking dsb = DiffSearchBooking.createObject(line);

				if (dsb != null) {
					bookings.clear();

					mamount = dsb.getAbsolutAmount();

					while ((dsb != null) 
							 && (dsb.getAbsolutAmount().compareTo(mamount) == 0)) {
						bookings.add(dsb);

						line = dis.readLine();

						if (line != null) {
							dsb = DiffSearchBooking.createObject(line);
						} else {
							dsb = null;
						} 
					} 

					if (bookings.size() > 0) {
						if (checkTotal(bookings)) {
							printList(bookings);
						} 
					} 
				} else {
					line = dis.readLine();
				} 
			} 

			dis.close();
		} catch (FileNotFoundException nfe) {
			System.err.println(nfe);
		} catch (IOException e) {
			System.err.println(e);
		} 
	} 

}