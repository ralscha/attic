package jsp_paper;

public class InventoryBean {

    /*
      Catalogue of produce names. 
    */
    private static final String[] names = 
    { "yellow onions", "red onions", "carrots", 
      "spinach", "broccoli", "eggplant", 
      "okra", "beans", "cilantro", 
      "squash", "lettuce", "cauliflower",
      "green pepper", "red pepper", "ginger",
      "garlic" };

    /*
      Catalogue of sku#s for produce. 
    */
    private static final int[] skus = 
    { 1, 2, 3, 4, 5, 6, 7, 8,
      9, 10, 11, 12, 13, 14, 15, 16 };

    /*
      Catalogue of prices/lb of produce. 
    */
    private static final double[] prices = 
    { 2.19, 1.29, 2.25, 1.76, 
      1.89, 2.14, 0.89, 2.67,
      2.21, 1.43, 0.75, 2.10,
      1.35, 1.35, 0.50, 0.45 };

    /*
      Catalogue of produce products. 
    */
    private Product[] catalogue_ = null;

    /*
      Use Singleton Pattern to create Inventory only once 
      and allow global access to the catalogue.
    */
    private static InventoryBean inventory_ = new InventoryBean();
    private InventoryBean() {
	catalogue_ = new Product[skus.length];
	for (int i = 0; i < skus.length; i++) {
	    catalogue_[i] = createProduct(skus[i]);
	}
    }
  
    public static Product[] getCatalogue() {
	return inventory_.catalogue_;
    }

    /*
      Use Factory-method pattern to create Produce Products.
      This hides the details of how the product is created.
      Subclasses can override this method to produce other
      products besides produce.
    */
    private Product createProduct(int sku) {
	 return new Product(sku, names[sku-1], prices[sku-1]);
     }
}
