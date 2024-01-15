
package supply;

public class Buyer
{
    private String name;
    private int    desiredQuantity;
    private int    boughtQuantity;
    private double price;

    public Buyer(String name,
                  int desiredQuantity,
                  double price)
    {
        this.name            = name;
        this.desiredQuantity = desiredQuantity;
        this.price           = price;
    }

    public String getName()
    {
        return this.name;
    }

    public int getDesiredQuantity()
    {
        return this.desiredQuantity;
    }

    public double getPrice()
    {
        return this.price;
    }

    public int buy(int quantity)
    {
        this.boughtQuantity += quantity;

        this.desiredQuantity -= quantity;

        return quantity;
    }

    public String toString()
    {
        return "[Buyer: name='" + getName() + "' bought='" + this.boughtQuantity + "'; desired='" + this.desiredQuantity + "'; price='" + this.price + "']";
    }
}
