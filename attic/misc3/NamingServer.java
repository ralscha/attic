import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import com.csg.cs.corba.NSWrapper.*;
import java.io.*;

public class NamingServer {
	public static void main(String args[]) {
		//initialize ORB
		
		try {
			
			ORB orb = ORB.init(args, null);
	
			
			Import imp = new Import(args);
			org.omg.CORBA.Object obj = imp.getReference("CIF.Service/CIF.Instanz/CIF_1_0");
			
			PrintWriter pw = new PrintWriter(new FileWriter("cif.ior"));
			pw.println(orb.object_to_string(obj));
			pw.close();
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}