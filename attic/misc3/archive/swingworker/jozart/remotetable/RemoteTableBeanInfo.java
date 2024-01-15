/*
 * RemoteTableBeanInfo.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.remotetable;

import java.awt.Image;
import java.beans.*;

/**
 * A BeanInfo class for the RemoteTable JavaBean.
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class RemoteTableBeanInfo extends SimpleBeanInfo {

    /** 16x16 color icon. */
    private Image icon;
    /** 32x32 color icon. */
    private Image icon32;
    /** 16x16 mono icon. */
    private Image iconM;
    /** 32x32 mono icon. */
    private Image icon32M;

    /**
     * Constructs a new BeanInfo class for the Puzzle JavaBean.
     */
    public RemoteTableBeanInfo() {
        iconM   = icon   = loadImage ("RemoteTableC16.gif");
        icon32M = icon32 = loadImage ("RemoteTableC32.gif");
    }

    /**
     * Returns BeanInfo for the superclass of this bean.
     */
    public BeanInfo[] getAdditionalBeanInfo() {
        try {
            return new BeanInfo[] {
                Introspector.getBeanInfo(RemoteTable.class.getSuperclass())
            };
        } catch (IntrospectionException ex) {
            ex.printStackTrace ();
            return null;
        }
    }

    /**
     * Returns an image object that can be used to represent
     * the bean in toolboxes, toolbars, etc.
     */
    public Image getIcon (int iconKind) {
        switch (iconKind) {
            case ICON_COLOR_16x16: return icon;
            case ICON_COLOR_32x32: return icon32;
            case ICON_MONO_16x16: return iconM;
            case ICON_MONO_32x32: return icon32M;
        }
        return null;
    }

    /**
     * Returns an array of PropertyDescriptors describing the
     * editable properties supported by this bean.
     */
    public PropertyDescriptor[] getPropertyDescriptors () {
        PropertyDescriptor[] propertyDescriptors = new PropertyDescriptor[2];
        try {
            propertyDescriptors [0] = new PropertyDescriptor ("pendingBackground", RemoteTable.class);
            propertyDescriptors [0].setBound (true);
            propertyDescriptors [0].setConstrained (false);
            propertyDescriptors [1] = new PropertyDescriptor ("pendingForeground", RemoteTable.class);
            propertyDescriptors [1].setBound (true);
            propertyDescriptors [1].setConstrained (false);
        } catch (IntrospectionException ex) {
            ex.printStackTrace ();
            return null;
        }
        return propertyDescriptors;
    }

}

