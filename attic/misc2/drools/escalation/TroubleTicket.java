
package escalation;

public class TroubleTicket
{
    public static final int NEW      = 1;
    public static final int NOTIFIED = 2;
    public static final int NULL     = 3;

    private String submitter;
    private int    status;

    public TroubleTicket(String submitter)
    {
        this.submitter = submitter;
        this.status    = NEW;
    }

    public String getSubmitter()
    {
        return this.submitter;
    }

    public int getStatus()
    {
        return this.status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String toString()
    {
        StringBuffer buf = new StringBuffer();

        buf.append( "[TroubleTicket: submitter='" );
        buf.append( getSubmitter() );
        buf.append( "'; status='" );

        switch ( getStatus() )
        {
            case (NEW) :
            {
                buf.append( "NEW" );
                break;
            }
            case (NOTIFIED):
            {
                buf.append( "NOTIFIED" );
                break;
            }
            case (NULL):
            {
                buf.append( "/dev/null" );
                break;
            }
        }

        buf.append( "']" );

        return buf.toString();
    }
}
