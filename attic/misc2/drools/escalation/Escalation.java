package escalation;

import org.drools.RuleBase;
import org.drools.WorkingMemory;
import org.drools.DroolsException;
import org.drools.AssertionException;

import org.drools.semantic.java.RuleLoader;

import java.io.File;
import java.net.MalformedURLException;

public class Escalation
{
    public static void main(String[] args)
    {
        if ( args.length != 1 )
        {
            System.err.println( "usage: java Escalation <rule-set.xml>" );

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
                TroubleTicket bobTicket  = new TroubleTicket( "bob" );
                TroubleTicket daveTicket = new TroubleTicket( "dave" ); 

                System.err.println( "----------------------------------------");
                System.err.println( "    PRE" );
                System.err.println( "----------------------------------------");

                System.err.println( bobTicket );
                System.err.println( daveTicket );
                
                System.err.println( "----------------------------------------");

                // Now, simply assert them into the [org.drools.WorkingMemory]
                // and let the logic engine do the rest.

                workingMemory.assertObject( daveTicket );
                workingMemory.assertObject( bobTicket );

                System.err.println( "----------------------------------------");
                System.err.println( "    POST ASSERT" );
                System.err.println( "----------------------------------------");

                System.err.println( bobTicket );
                System.err.println( daveTicket );

                System.err.println( "----------------------------------------");

                try
                {
                    System.err.println( "[[ Sleeping 10 seconds ]]" );
                    Thread.sleep( 10000 );
                    System.err.println( "[[ Done sleeping ]]" ) ;
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                System.err.println( "----------------------------------------");
                System.err.println( "    POST SLEEP" );
                System.err.println( "----------------------------------------");

                System.err.println( bobTicket );
                System.err.println( daveTicket );

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
