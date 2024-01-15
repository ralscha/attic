import com.jscape.inet.JVerify;

public class Example1
{
    public static void main(String[] args)
    {
        JVerify jv = new JVerify();
        jv.setVerification(JVerify.SYNTAX);
        jv.setEmail("user@domain.ch");
        try
        {
            jv.verify();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

}

