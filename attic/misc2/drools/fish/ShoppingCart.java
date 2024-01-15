
package fish;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class ShoppingCart
{
    private List items;

    private double discount;

    public ShoppingCart()
    {
        this.items    = new ArrayList();
        this.discount = 0;
    }

    public void setDiscount(double discount)
    {
        this.discount = discount;
    }

    public double getDiscount()
    {
        return this.discount;
    }

    public void addItem(CartItem item)
    {
        this.items.add( item );
    }

    public List getItems()
    {
        return this.items;
    }

    public List getItems(String name)
    {
        ArrayList matching = new ArrayList();

        Iterator itemIter = getItems().iterator();
        CartItem eachItem = null;

        while ( itemIter.hasNext() )
        {
            eachItem = (CartItem) itemIter.next();

            if ( eachItem.getName().equals( name ) )
            {
                matching.add( eachItem );
            }
        }

        return matching;
    }

    public double getGrossCost()
    {
        Iterator itemIter = getItems().iterator();
        CartItem eachItem = null;

        double cost = 0.00;

        while ( itemIter.hasNext() )
        {
            eachItem = (CartItem) itemIter.next();

            cost += eachItem.getCost();
        }

        return cost;
    }

    public double getDiscountedCost()
    {
        double cost     = getGrossCost();
        double discount = getDiscount();

        double discountedCost = cost * ( 1 - discount );

        return discountedCost;
    }

    public String toString()
    {
        StringBuffer buf = new StringBuffer();

        buf.append( "[ShoppingCart:\n");
        buf.append( "\t      gross total=" + getGrossCost() + "\n" );
        buf.append( "\t discounted total=" + getDiscountedCost() + "\n" );

        Iterator itemIter = getItems().iterator();

        while ( itemIter.hasNext() )
        {
            buf.append( "\t" + itemIter.next() + "\n" );
        }

        buf.append( "]" );

        return buf.toString();
    }
}
