
import javax.swing.*;
import common.util.*;
import common.swing.*;

public class BookPanel extends JPanel {

	protected final static String[] serviceCenters;
	protected final static String[] iso;
	protected final static String[] bu;
	protected final static String[] gtfType;
	
	static {
		
		gtfType = ProductMap.getProducts();
				
		java.util.List list = AppProperties.getStringArrayProperty("bu.code");
		bu = (String[])list.toArray(new String[list.size()]);

		list = AppProperties.getStringArrayProperty("service.center");
		serviceCenters = (String[])list.toArray(new String[list.size()]);
		
		iso = CurrencyMap.getISOCodes();
		
	}

/*
	public BookPanel() {
		super();
	}
*/
	
}