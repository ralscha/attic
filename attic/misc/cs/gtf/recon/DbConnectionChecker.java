package gtf.recon;

/**
 * This type was created in VisualAge.
 */

import java.sql.*;
import com.sun.java.util.collections.*;
 
public class DbConnectionChecker extends Reconciliation {

/**
 * This method was created in VisualAge.
 */
private void check(String center) {
	ServiceCenters sc = new ServiceCenters();
	
	System.out.print("testing " + center + ".... ");
	try {
		gdc.connect(center);
		System.out.println("ok");
	} catch (Exception e) {
		System.out.println(e);
	}
}
/**
 * This method was created in VisualAge.
 * @param args java.lang.String[]
 */
public static void main(String args[]) {

	if (args.length == 1) {
		List serviceCenters = createServiceCenterList(args[0]);
		if (!serviceCenters.isEmpty())
			new DbConnectionChecker().start(serviceCenters);
	}
	else 		
		System.out.println("java DbConnectionChecker SC [,SC,SC,..]");
}
protected boolean start(List serviceCenters) {
	Iterator it = serviceCenters.iterator();
	String center = null;
	while (it.hasNext()) {
		center = (String) it.next();
		check(center);
	}
	return false;
}
}