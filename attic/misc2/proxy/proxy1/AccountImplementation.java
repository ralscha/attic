// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AccountImplementation.java

package proxy1;


// Referenced classes of package proxy1:
//            InsufficientFundsException, Account

public class AccountImplementation
    implements Account
{

    public AccountImplementation()
    {
    }

    public Double getBalance()
    {
        return new Double(balance);
    }

    public void deposit(Double amount)
    {
        balance += amount.doubleValue();
    }

    public void withdraw(Double amount)
        throws InsufficientFundsException
    {
        if(getBalance().compareTo(amount) < 0)
        {
            throw new InsufficientFundsException();
        } else
        {
            balance -= amount.doubleValue();
            return;
        }
    }

    private double balance;
}
