// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FractionalLayout.java

package com.bdnm.awt;

import java.awt.*;
import java.util.Hashtable;

// Referenced classes of package com.bdnm.awt:
//            OriginConstraint

public class FractionalLayout
    implements LayoutManager
{

    public FractionalLayout()
    {
        components = new Hashtable();
    }

    public void setConstraint(Component component, OriginConstraint originconstraint)
    {
        components.put(component, originconstraint);
    }

    public OriginConstraint getConstraint(Component component)
    {
        OriginConstraint originconstraint = (OriginConstraint)components.get(component);
        if(originconstraint == null)
        {
            originconstraint = new OriginConstraint();
            setConstraint(component, originconstraint);
        }
        return originconstraint;
    }

    public void addLayoutComponent(String s, Component component)
    {
        setConstraint(component, new OriginConstraint());
    }

    public void removeLayoutComponent(Component component)
    {
        components.remove(component);
    }

    public Dimension preferredLayoutSize(Container container)
    {
        Insets insets = container.getInsets();
        int i = container.getComponentCount();
        Dimension dimension = new Dimension();
        for(int j = 0; j < i; j++)
        {
            Component component = container.getComponent(j);
            OriginConstraint originconstraint = getConstraint(component);
            Dimension dimension1 = originconstraint.containSize(component.getPreferredSize());
            dimension.width = Math.max(dimension.width, dimension1.width);
            dimension.height = Math.max(dimension.height, dimension1.height);
        }

        dimension.width += insets.left + insets.right;
        dimension.height += insets.top + insets.bottom;
        return dimension;
    }

    public Dimension minimumLayoutSize(Container container)
    {
        Insets insets = container.getInsets();
        int i = container.getComponentCount();
        Dimension dimension = new Dimension();
        for(int j = 0; j < i; j++)
        {
            Component component = container.getComponent(j);
            OriginConstraint originconstraint = getConstraint(component);
            Dimension dimension1 = originconstraint.containSize(component.getMinimumSize());
            dimension.width = Math.max(dimension.width, dimension1.width);
            dimension.height = Math.max(dimension.height, dimension1.height);
        }

        dimension.width += insets.left + insets.right;
        dimension.height += insets.top + insets.bottom;
        return dimension;
    }

    public void layoutContainer(Container container)
    {
        Insets insets = container.getInsets();
        int i = container.getComponentCount();
        Dimension dimension = container.getSize();
        dimension.width -= insets.left + insets.right;
        dimension.height -= insets.top + insets.bottom;
        for(int j = 0; j < i; j++)
        {
            Component component = container.getComponent(j);
            OriginConstraint originconstraint = getConstraint(component);
            Rectangle rectangle = originconstraint.adjustedRectangle(dimension, component.getPreferredSize());
            component.setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }

    }

    protected Hashtable components;
}
