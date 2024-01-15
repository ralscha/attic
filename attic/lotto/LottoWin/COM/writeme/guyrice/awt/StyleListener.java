// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StyleListener.java

package COM.writeme.guyrice.awt;

import java.util.EventListener;

// Referenced classes of package COM.writeme.guyrice.awt:
//            StyleEvent

public interface StyleListener
    extends EventListener
{
    public abstract void styleChanged(StyleEvent styleevent);

}
