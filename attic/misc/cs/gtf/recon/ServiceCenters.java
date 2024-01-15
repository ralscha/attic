package gtf.recon;

/**
 * This type was created in VisualAge.
 */
import java.util.*;
import gtf.recon.util.*;
 
public class ServiceCenters {
	private static Vector serviceCenters = null;
/**
 * This method was created in VisualAge.
 */
 public ServiceCenters() {	
	
	if (serviceCenters == null) {
		serviceCenters = new Vector();

		String b = AppProperties.getProperty("serviceCenters");
		
		StringTokenizer st = new StringTokenizer(b, ",");
		while (st.hasMoreTokens()) { 
			serviceCenters.addElement(st.nextToken());		
		}
	}
}
/**
 * This method was created in VisualAge.
 * @return boolean
 * @param center java.lang.String
 */
public boolean exists(String center) {
	if (serviceCenters != null)
		return (serviceCenters.contains(center));
	else
		return false;
}
/**
 * This method was created in VisualAge.
 * @return java.lang.String[]
 */
public String[] getAllServiceCenters() {
	String[] temp = new String[serviceCenters.size()];

	serviceCenters.copyInto(temp);
	return temp;
}
/**
 * This method was created in VisualAge.
 * @return java.lang.String
 * @param center java.lang.String
 */
public String getDbName(String center) {
	return AppProperties.getProperty("dbName"+center).trim();
}
/**
 * This method was created in VisualAge.
 * @return java.lang.String
 * @param center java.lang.String
 */
public String getIBBB(String center) {
	return AppProperties.getProperty("ibbb"+center).trim();
}
/**
 * This method was created in VisualAge.
 * @return java.lang.String
 * @param center java.lang.String
 */
public String getIP(String center) {
	return AppProperties.getProperty("ip"+center).trim();
}
/**
 * This method was created in VisualAge.
 * @return java.lang.String
 * @param center java.lang.String
 */
public String getPassword(String center) {
	return AppProperties.getProperty("pw"+center).trim();
}
/**
 * This method was created in VisualAge.
 * @return java.lang.String
 * @param center java.lang.String
 */
public String getUser(String center) {
	return AppProperties.getProperty("user"+center).trim();
}
}