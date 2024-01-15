
package supply;

public class Seller
{
    private String name;
    private int    quantity;
    private double price;

    public Seller(String name,
                  int quantity,
                  double price)
    {
        this.name     = name;
        this.quantity = quantity;
        this.price    = price;
    }

    public String getName()
    {
        return this.name;
    }

    public int getQuantity()
    {
        return this.quantity;
    }

    public double getPrice()
    {
        return this.price;
    }

    public int sell(int desiredQuantity)
    {
        int numSold = 0;

        if ( desiredQuantity > this.quantity )
        {
            numSold = this.quantity;
            this.quantity = 0;
        }
        else
        {
            numSold = desiredQuantity;
            this.quantity -= desiredQuantity;
        }

        return numSold;
    }

    public String toString()
    {
        return "[Seller: name='" + getName() + "' on-hand='" + this.quantity + "'; price='" + this.price + "']";
    }
}
