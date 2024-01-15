// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Account.java

package proxy2;


// Referenced classes of package proxy2:
//            ProxyTarget, InsufficientFundsException

public interface Account
    extends ProxyTarget
{

    public abstract Double getBalance();

    public abstract void deposit(Double double1);

    public abstract void withdraw(Double double1)
        throws InsufficientFundsException;
}
