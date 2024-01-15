package gtf.mbooker;

import java.util.*;
import java.io.*;

import common.util.*;

public final class ProductMap {
	
	private static ProductMap instance = new ProductMap();
	
	private Map productMap;
	
	private ProductMap() {
		productMap = new HashMap();
			
		String line;
		InputStream is = getClass().getResourceAsStream("products.dat");
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));

			while ((line = buf.readLine()) != null) {
				java.util.StringTokenizer st = new java.util.StringTokenizer(line.trim(), ",");
				productMap.put(st.nextToken(), st.nextToken());
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
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