package jsp_paper;

public class Product implements Cloneable { 
    private int sku_ = 0;
    private String name_ = null;
    private double price_ = 0;
    private double pounds_ = 0.0;

    public String toString() {
	return "SKU: " + sku_ + ", Name: " + name_ + 
	    ", Price: " + price_ + ", Pounds: " + pounds_;
    }

    public Product(int sku, 
		   String name,
		   double price) {
	sku_ = sku;
	name_ = name;
	price_ = price;
    }
    
    public int getSKU() {
	return sku_;
    }

    public String getName() {
	return name_;
    }

    public double getPrice() {
	return price_;
    }
    
    public double getPounds() {
	return pounds_;
    }

    public void setPounds(double pounds) {
	pounds_ = pounds;
    }

    /*
      Make clone function public.
    */
    public Object clone() {
	Object dup = null;
	try {
	    dup = super.clone();
	}
	catch(CloneNotSupportedException ignore) { }

	return dup;
    }
}
