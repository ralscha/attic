import java.beans.*;

public class BeanTester {

	public static void main(String[] args) {
    try {
		  Beans.instantiate(null, "com.ibm.jsp.NumberFormatGeneral");
    } catch (Exception e) {
      System.err.println(e);
    }

	}
}