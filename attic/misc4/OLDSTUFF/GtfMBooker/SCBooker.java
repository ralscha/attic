import java.util.*;
import java.io.*;

public class SCBooker {

	
	SCBooker() {
		
	}
	
	private void createOutBookings(String fileName, int seqno) {
		
		BufferedReader dis;
		String line;
	
		try {
			dis = new BufferedReader(new FileReader(fileName));
			while ((line = dis.readLine()) != null) {
				ContingentLiability cl = new ContingentLiability();
				/*cl.setSequence_number(seqno++);
				cl.setType("C");
				cl.setGtf_number(line.substring(5));
				cl.setAccount_number(line);
				cl.setExpiry_date();
				cl.setExchange_rate();
				cl.setGtf_proc_center();
				cl.setCustomer_ref();
				cl.setGtf_type();
				cl.setDossier_item();
				cl.setBu_code();
				*/
			}
			dis.close();
			
		} catch(Exception e) {
			System.err.println(e);
		}
		
		

	}
	
	private void createInBookings() {
	
	}
	
	public static void main(String[] args) {
	
	}

}