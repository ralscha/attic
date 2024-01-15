package gtf.mbooker;

import java.util.*;

import common.util.*;

public final class ProductMap {
	
	private static ProductMap instance = new ProductMap();
	
	private Map productMap;
	
	private ProductMap() {
		productMap = new HashMap();
			
		Iterator it = AppProperties.getStringArrayProperty("product.code").iterator();
		while(it.hasNext()) {
			String product = (String)it.next();
			StringTokenizer st = new StringTokenizer(product.trim(), ",");
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
	}

	public static String getProductCodeNo(String product) {
		return (String)instance.productMap.get(product);
	}
	
	public static void main(String args[]) {
		System.out.println(getProductCode("loi"));
		System.out.println(getProducts());
	}
}