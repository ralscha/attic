// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AccountFactory.java

package proxy1;

import java.lang.reflect.Proxy;

// Referenced classes of package proxy1:
//            Account, AccountImplementation, TracerInvocationHandler

public class AccountFactory
{

    public AccountFactory()
    {
    }

    public static Account createAccount()
    {
        Account account = new AccountImplementation();
        String traceProperty = System.getProperty("TRACE_ON");
        if(traceProperty != null && traceProperty.equals("Y"))
        {
            TracerInvocationHandler tracer = new TracerInvocationHandler(account);
            Account proxy = (Account)Proxy.newProxyInstance(Account.class.getClassLoader(), new Class[]{Account.class
            }, tracer);            

            return proxy;
        } else
        {
            return account;
        }
    }

  
}
