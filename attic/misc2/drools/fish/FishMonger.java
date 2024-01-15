package fish;

import org.drools.RuleBase;
import org.drools.WorkingMemory;
import org.drools.DroolsException;
import org.drools.AssertionException;

import org.drools.semantic.java.RuleLoader;

import java.io.File;
import java.net.MalformedURLException;

public class FishMonger
{
    public static void main(String[] args)
    {
        if ( args.length != 1 )
        {
            System.err.println( "usage: java FishMonger <rule-set.xml>" );

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

            try
            {
                ShoppingCart cart = new ShoppingCart();

                cart.addItem( new CartItem( "tropical fish",
                                            12.99 ) );
                
                cart.addItem( new CartItem( "tropical fish",
                                            12.99 ) );
                
                cart.addItem( new CartItem( "tropical fish",
                                            12.99 ) );
                
                cart.addItem( new CartItem( "tropical fish",
                                            12.99 ) );

                if ( false )
                {
                    cart.addItem( new CartItem( "tropical fish food",
                                                8.00 ) );
                }

                if ( false )
                {
                    cart.addItem( new CartItem( "tank",
                                                80.00 ) );
                }
                              

                System.err.println( "----------------------------------------");
                System.err.println( "    PRE" );
                System.err.println( "----------------------------------------");

                System.err.println( cart );
                
                System.err.println( "----------------------------------------");

                // Now, simply assert them into the [org.drools.WorkingMemory]
                // and let the logic engine do the rest.

                workingMemory.assertObject( cart );

                System.err.println( "----------------------------------------");
                System.err.println( "    POST" );
                System.err.println( "----------------------------------------");

                System.err.println( cart );

                System.err.println( "----------------------------------------");
            }
            catch (AssertionException e)
            {
                e.printStackTrace();
            }
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
