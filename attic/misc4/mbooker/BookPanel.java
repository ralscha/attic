package gtf.mbooker;

import javax.swing.*;
import gtf.common.*;
import common.util.*;
import common.swing.*;

public class BookPanel extends JPanel {

	protected final static String[] serviceCenters;
	protected final static String[] iso;
	protected final static String[] bu;
	protected final static String[] gtfType;
	
	static {
		
		gtfType = ProductMap.getProducts();
				
		java.util.List list = AppProperties.getStringArrayProperty(gtf.common.Constants.P_BU_CODES);
		bu = (String[])list.toArray(new String[list.size()]);

		ServiceCenter[] scs = new ServiceCenters().getAllServiceCenters();
		serviceCenters = new String[scs.length];
		
		for (int i = 0; i < scs.length; i++) {
			serviceCenters[i] = scs[i].getName();	
		}
		
		iso = CurrencyMap.getISOCodes();
		
	}

	
}