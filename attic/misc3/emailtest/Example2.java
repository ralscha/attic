import com.jscape.inet.JVerify;

public class Example2
{
    public static void main(String[] args)
    {
        /* Create new JVerify instance */
        JVerify jv = new JVerify();
        
        /* Use Domain Level Verification */
        jv.setVerification(JVerify.USER);
        
        /* Set DNS Server */
        jv.setNameServer("195.141.188.20");        
        
        /* Set Email to verify */
        jv.setEmail("sr@dfd.@.ch");
        jv.setTimeout(10000);
        
        try
        {
            /* Verify Email */
            jv.verify();
            System.out.println("OK");
        }
        catch(Exception e)
        {
            /* Handle any verification exceptions */
            System.out.println(e);
        }
    }
}
