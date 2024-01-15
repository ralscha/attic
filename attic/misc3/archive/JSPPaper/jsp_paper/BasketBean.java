package jsp_paper;

import javax.servlet.http.HttpServletRequest;
import java.util.Hashtable;
import java.util.Enumeration;

public class BasketBean {
  final static public String BASKET = "Basket";
  final static public String PAGE = "Page";

  /*
    States in the workflow.
  */
  final static public String UPDATE = "Update";
  final static public String PURCHASE = "Purchase";
  final static public String RECEIPT = "Receipt";

  /* 
     Current set of products in the basket. 
     Key: SKU# Value: Product
  */
  private Hashtable products_ = new Hashtable();
    	   
  public BasketBean() {
  }

  /* 
     Calculate the total price of contents of basket. 
  */
  public double getTotal() {
    double totalPrice = 0.0;
    Enumeration e = products_.elements();
    while(e.hasMoreElements()) {
      Product product = (Product)e.nextElement();
      totalPrice += product.getPounds() * product.getPrice();
    }
    return totalPrice;
  }

  /*
    Get the #pounds in the basket of a certain product.
  */
  public double getPounds(Product p_in_inv) {
    int SKU = p_in_inv.getSKU();
    Product p = (Product)products_.get(Integer.toString(SKU));
    if(p == null)
      return 0.0;
    else
      return p.getPounds();
  }

  /*
    Alter basket contents with current selections.
  */
  public void savePurchases(HttpServletRequest request) {
    Product[] products = InventoryBean.getCatalogue();
    String[] lbValues = request.getParameterValues("pounds");
    if (lbValues != null) {
      products_.clear();
      for (int i = 0; i < lbValues.length; i++) {
	      double lbs = Double.parseDouble(lbValues[i]);
	      if(lbs > 0) {
	        Product p = null;
	        p = (Product)products[i].clone();
	        p.setPounds(lbs);
	        products_.put(Integer.toString(p.getSKU()), p);
	      }   
      }
    }
  }

  /* 
     Convenience function. Produce a double value as string with just two 
     decimal digits. 
  */
  public static String getStringifiedValue(double value) {
    String subval = "0.00";
    if (value > 0.0) {
      subval = Double.toString(value);
      int decimal_len = subval.length() - (subval.lastIndexOf('.') + 1);
      if(decimal_len > 1)
	      subval = subval.substring(0, subval.lastIndexOf('.') + 3);
      else
	      subval += "0";
    }
    return subval;
  }
    
  /* Clear basket of any purchases */
  public void clear() {
    products_.clear();
  }
}
