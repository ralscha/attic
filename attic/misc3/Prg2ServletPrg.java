
import java.io.*;
import java.net.*;
import java.util.*;

public class Prg2ServletPrg {

	public static void main(String[] args) {
		try {

			//connect
			URL url = new URL("http://localhost:8100/servlet/Prg2Servlet");

			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/octet-stream");

			//Open output stream and send some data.
			OutputStream out = conn.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);
			//SEND
			oos.writeObject(getAppointmentList());
			
			oos.flush();
			out.flush();
			out.close();

			//Open input stream and read the data back.
			InputStream in = conn.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(in);
			//Here you would read the data back.
			Status status = (Status)ois.readObject();			
			ois.close();
			in.close();
			
			System.out.println(status.getMessage());
			System.out.println("Verarbeitet : " + status.getNo());

		} catch (Exception e) {
			System.err.println(e);
		}
	
	}
	
	private static List getAppointmentList() {
		List appList = new ArrayList();
		
		appList.add(new Appointment("12.12.1999", "13.12.1999", "ABB"));
		appList.add(new Appointment("12.12.1999", "13.12.1999", "ABB"));
		appList.add(new Appointment("12.12.1999", "13.12.1999", "ABB"));
		appList.add(new Appointment("12.12.1999", "13.12.1999", "ABB"));
		appList.add(new Appointment("12.12.1999", "13.12.1999", "ABB"));
		appList.add(new Appointment("12.12.1999", "13.12.1999", "ABB"));
		appList.add(new Appointment("12.12.1999", "13.12.1999", "ABB"));
		appList.add(new Appointment("12.12.1999", "13.12.1999", "ABB"));
		appList.add(new Appointment("12.12.1999", "13.12.1999", "ABB"));
		appList.add(new Appointment("12.12.1999", "13.12.1999", "ABB"));
		appList.add(new Appointment("12.12.1999", "13.12.1999", "ABB"));
		appList.add(new Appointment("12.12.1999", "13.12.1999", "ABB"));																				
		
		return appList;
	}
}