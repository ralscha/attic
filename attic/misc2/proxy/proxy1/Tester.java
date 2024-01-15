// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Tester.java

package proxy1;


// Referenced classes of package proxy1:
//            Account, AccountFactory

public class Tester
{

    public Tester()
    {
    }

    public static void main(String args[])
    {
        try
        {
            for(int i = 0; i < args.length; i++)
                if(args[i].lastIndexOf("-trace") != -1)
                    System.setProperty("TRACE_ON", "Y");

            Account account = AccountFactory.createAccount();
            account.deposit(new Double(100D));
            account.withdraw(new Double(60D));
            account.getBalance();
            account.withdraw(new Double(60D));
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
    }
}
