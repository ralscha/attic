/*

Class CrowdController   (C) L. Vanhelsuwe  email: lva@telework.demon.co.uk
---------------------
This re-usable class enforces a restricted number of clients simultaneously
using some unspecified resource.

Say if you had an open-ended (unlimited) recursive mechanism spawning
threads, you would want to ensure that only so many threads in total are
active at any one time. Class CrowdController can enforce such a limitation
by handing out "tickets" (or licenses) to clients, so that they can "proceed"
with whatever they wish. Entities applying for a ticket when no more tickets
are available will block until a ticket is released by another entity.

*/

public class CrowdController
{

    int ticketDatabase[];     // array of valid issueable tickets
    int crowdSize;            // current crowd size
    int maxCrowdSize;         // max crowd size to enforce

    //-----------------------------------------------------------------------
    // Constructor.
    // Given the maximum size of a crowd to control, initialize the crowd to
    // empty and mark all tickets as "not issued".
    //-----------------------------------------------------------------------
    public CrowdController(int maxCrowdSize)
    {

        this.maxCrowdSize = maxCrowdSize;   // crowd size to enforce
        crowdSize         = 0;              // current size of crowd

        ticketDatabase = new int[maxCrowdSize];
        for(int i=0; i < maxCrowdSize; i++)
        {
            ticketDatabase[i] = -1;         // mark all ticket IDs as available
        }
    }

    //-----------------------------------------------------------------------
    // Apply for a ticket.
    // If tickets are still available, give client a ticket and record its
    // issue.
    // If all tickets are currently issued, block (put client thread to sleep)
    // until a ticket becomes available again (and gets recycled).
    // Tickets can only be returned via the returnTicket() method.
    //-----------------------------------------------------------------------
    public synchronized int getTicket()
    {

        // if crowd is already at full capacity: wait until crowd shrinks
        while (crowdSize == maxCrowdSize)
        {

            // System.out.println("Crowd too big.... you have to wait (" +
            //    Thread.currentThread().getName() + ")");

            try
            {
                wait();
            }
            catch (InterruptedException leaveUsAlonePlease) {}
        }

        // OK. crowd has shrunk a bit, so grab a ticket

        int ticket = findFreeTicket();
        if (ticket == -1)
        {
            System.out.println("Bug: could not find free ticket when we should be able to.");
        }
        ticketDatabase[ticket] = ticket;    // record ticket issue
        crowdSize++;                        // track # of tickets out there

        return ticket;
    }

    //-----------------------------------------------------------------------
    // A client has done whatever it needed to do and wants to give up its
    // ticket. Take ticket back and recycle it for future clients. Wake up
    // a random thread waiting to get a ticket of its own.
    //-----------------------------------------------------------------------
    public synchronized void returnTicket(int ticket)
                                        throws IllegalArgumentException
    {

        String illegalTicket = "Alert: illegal ticket ID seized! (id= " + ticket + ")";

        if (crowdSize == 0)
        {
            throw new IllegalArgumentException(illegalTicket + " because crowdSize was 0");
        }

        if (ticketDatabase[ticket] != ticket)
        {
            throw new IllegalArgumentException(illegalTicket + " because ticket ID is not issued!");
        }

        ticketDatabase[ticket] = -1;    // mark ticket as available again.
        crowdSize--;

        notifyAll();                    // wake up a thread needing a ticket
    }

    //-----------------------------------------------------------------------
    // Find any ticket which hasn't been issued yet.
    //-----------------------------------------------------------------------
    protected int findFreeTicket()
    {

        for(int i=0; i < maxCrowdSize; i++)
        {
            if (ticketDatabase[i] == -1)
            {
                return i;
            }
        }
        return -1;
    }

}
