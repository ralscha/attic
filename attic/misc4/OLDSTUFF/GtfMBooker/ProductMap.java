
import java.util.*;
import java.io.*;

import common.util.*;

public final class ProductMap {
	
	private static ProductMap instance = new ProductMap();
	
	private Map productMap;
	
	private ProductMap() {
		productMap = new HashMap();
		
		List p1 = AppProperties.getStringArrayProperty("gtf.type");
		Iterator it = p1.iterator();
		while(it.hasNext()) {
			StringTokenizer st = new StringTokenizer((String)it.next(), ",");
			productMap.put(st.nextToken(), st.nextToken());
		}
		
		
	}
	
	public static String[] getProducts() {
		if (!instance.productMap.isEmpty()) {
			String[] ret = (String[])instance.productMap.keySet().toArray(new String[instance.productMap.size()]);		
			Arrays.sort(ret);
			return ret;
		} else
			return null;
	}
	
	public static String getProductCode(String product) {
		return product;
		
		//return (String)instance.productMap.get(product);
	}

}