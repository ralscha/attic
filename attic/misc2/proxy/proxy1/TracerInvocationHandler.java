// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TracerInvocationHandler.java

package proxy1;

import java.io.PrintStream;
import java.lang.reflect.*;

public class TracerInvocationHandler
    implements InvocationHandler
{

    public TracerInvocationHandler(Object target)
    {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object args[])
        throws Throwable
    {
        Object returnData = null;
        StringBuffer msg = new StringBuffer("START: ");
        msg.append(method.toString());
        if(args != null)
        {
            msg.append(" ARGS = [");
            for(int i = 0; i < args.length; i++)
            {
                msg.append(args[i]);
                msg.append(" ");
            }

            msg.setLength(msg.length() - 1);
            msg.append("]");
        }
        System.out.println(msg);
        try
        {
            returnData = method.invoke(target, args);
        }
        catch(InvocationTargetException itx)
        {
            System.out.println("RETHROWING from ".concat(String.valueOf(method.toString())));
            throw itx.getTargetException();
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
        finally
        {
            System.out.println("FINISH: ".concat(String.valueOf(method.toString())));
        }
        return returnData;
    }

    private Object target;
}
