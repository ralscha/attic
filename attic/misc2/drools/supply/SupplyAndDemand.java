
package supply;

import org.drools.RuleBase;
import org.drools.WorkingMemory;
import org.drools.DroolsException;
import org.drools.AssertionException;

import org.drools.semantic.java.RuleLoader;

import java.io.File;
import java.net.MalformedURLException;

public class SupplyAndDemand
{
    public static void main(String[] args)
    {
        if ( args.length != 1 )
        {
            System.err.println( "usage: java SupplyAndDemand <rule-set.xml>" );

            System.exit( 1 );
        }

        try
        {
            // First, construct an empty RuleBase to be the
            // container for your rule logic.

            RuleBase ruleBase = new RuleBase();
            
            // Then, use the [org.drools.semantic.java.RuleLoader]
            // static method to load a rule-set from a local File.
            
            RuleLoader.load( ruleBase,
                             new File( args[0] ) );

            // Create a [org.drools.WorkingMemory] to be the
            // container for your facts
            
            WorkingMemory workingMemory = ruleBase.createWorkingMemory();

            // Create some fact objects.

            // buyer-1 wants 100 items for no more than $9.99 each
            // buyer-2 wants 20 items for no more than $15.00 each

            // seller-1 has 50 items to sell for no less than $9.99 each
            // seller-2 has 75 items to sell for no less than $15.00 each
            // seller-3 has 20 items to sell for no less than $5.00 each

            Buyer buyer1 = new Buyer( "buyer-1",
                                      100,
                                      9.99 );

            Buyer buyer2 = new Buyer( "buyer-2",
                                      100,
                                      15.00 );

            Seller seller1 = new Seller( "seller-1",
                                         150,
                                         9.99 );

            Seller seller2 = new Seller ( "seller-2",
                                          75,
                                          15.00 );

            Seller seller3 = new Seller( "seller-3",
                                         20,
                                         5.00 );

            System.err.println( "----------------------------------------" );
            System.err.println( "    PRE" );
            System.err.println( "----------------------------------------" );
            System.err.println( buyer1 );
            System.err.println( buyer2 );
            System.err.println( seller1 );
            System.err.println( seller2 );
            System.err.println( seller3 );

            System.err.println( "----------------------------------------" );

            try
            {
                // Now, simply assert them into the [org.drools.WorkingMemory]
                // and let the logic engine do the rest.

                workingMemory.assertObject( buyer1 );
                workingMemory.assertObject( seller1 );
                workingMemory.assertObject( seller2 );
                workingMemory.assertObject( seller3 );
                workingMemory.assertObject( buyer2 );
            }
            catch (AssertionException e)
            {
                e.printStackTrace();
            }

            System.err.println( "----------------------------------------" );
            System.err.println( "    POST" );
            System.err.println( "----------------------------------------" );

            System.err.println( buyer1 );
            System.err.println( buyer2 );
            System.err.println( seller1 );
            System.err.println( seller2 );
            System.err.println( seller3 );

            System.err.println( "----------------------------------------" );
        }
        catch (DroolsException e)
        {
            e.printStackTrace();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }
}
