/*

Program: WebExplorer                       (C) 1996 Laurence Vanhelsuwe
--------------------
This Java application will, given an initial Web page URL, visit this
page and recursively visit all* pages refereced by it.
The names of the pages are listed on the console.

* = this implementation only hops to absolutely refereced pages using
anchor tags like <A HREF="http://...">.
Relative references (usually to documents on the same site) are not
followed at all. The prime goal of this program was to visit as many
sites as possible, and not to comprehensively chart every possible page.

Author email: LVA@telework.demon.co.uk

*/

public class WebExplorer
{

    final static String title =
         "WebExplorer v1.0  (c) 1996 L. Vanhelsuwe (lva@telework.demon.co.uk)";
    final static String title2 = "----------------";

    public static void main (String[] args)
    {

        System.out.println(title);
        System.out.println(title2);

        System.out.println("Ready..");

        new WebExplorer().doIt(args);
    }

    public void doIt (String[] args)
    {

        if (args.length == 0)
        {
            System.out.println("Usage: WebExplorer <URL>");
            System.exit(0);
        }

        // create the root instance of a PageVisitor thread. This thread
        // will load the initial page and spawn more threads for every link
        // found in that page.

        new PageVisitor( args[0] );
    }
}
