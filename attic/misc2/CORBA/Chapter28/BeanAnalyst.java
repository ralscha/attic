
import java.beans.*;
import java.awt.*;
import java.lang.reflect.*;

public class BeanAnalyst
{
 static public void main(String args[])
 {
   try
   {
    // Instantiate a bean
    Object s = Beans.instantiate(null, args[0]);

    // Introspect on class and stop after direct parent class
    BeanInfo bi  = Introspector.getBeanInfo(s.getClass(),
                                            s.getClass().getSuperclass());

    // Discover the display name
    BeanDescriptor bdsc = bi.getBeanDescriptor();
    System.out.println("Hi! Your display name is = " + bdsc.getDisplayName());

    // Get the bean's properties
    PropertyDescriptor[] pd = bi.getPropertyDescriptors();

    System.out.println("\n\nYour properties are: \n");
    for (int i = 0; i < pd.length; i++)
    {
       System.out.println("property = " + pd[i].getName() +
          "  (write method = " + pd[i].getWriteMethod().getName() +
          ", read method = " + pd[i].getReadMethod().getName() + ")");
    }

    // Get the bean's methods
    MethodDescriptor[]  md = bi.getMethodDescriptors();

    System.out.println("\n\nYour methods are: \n");
    for (int i = 0; i < md.length; i++)
    {
      System.out.println("" + md[i].getMethod() );
    }

    // Get the bean's events
    EventSetDescriptor[] evsd = bi.getEventSetDescriptors();
    Method[]  evm = evsd[0].getListenerMethods();

    System.out.println("\n\nYou emit the following Events: \n");
    for (int i = 0; i < evm.length; i++)
    {
      System.out.println("" + evm[i].getName() );
    }

    //Get the bean's icon types
    System.out.println("\n\nYou provide the following icon types: \n");
    if (bi.getIcon(BeanInfo.ICON_COLOR_32x32) != null)
      System.out.println("32x32 color");
    if (bi.getIcon(BeanInfo.ICON_COLOR_16x16) != null)
      System.out.println("16x16 color");
    if (bi.getIcon(BeanInfo.ICON_MONO_32x32) != null)
      System.out.println("32x32 mono");
    if (bi.getIcon(BeanInfo.ICON_MONO_16x16) != null)
      System.out.println("16x16 mono");

    if (Beans.isDesignTime())
      System.out.println("\n\nYour bean is in design-time mode.");
    else
      System.out.println("\n\nYour bean is in run-time mode.");

    if (Beans.isGuiAvailable())
      System.out.println("A GUI is available to your bean.");
    else
      System.out.println("A GUI is not available to your bean.");

    // Are you a persistent bean?
    Class myClass = s.getClass();
    boolean serializable = false;
    boolean externalizable = false;

    while (!myClass.getName().equals("java.lang.Object"))
    {
      Class[] interfaces = myClass.getInterfaces();
      for (int i = 0; i < interfaces.length; i++)
      {
         if (interfaces[i].getName().equals("java.io.Serializable"))
            serializable = true;

         if (interfaces[i].getName().equals("java.io.Externalizable"))
             externalizable = true;
      }
      myClass = myClass.getSuperclass();
    }

    if (serializable)
       System.out.println("You are a serializable persistent bean");
    if (externalizable)
       System.out.println("You are an externalizable persistent bean");

    System.exit(0);

   } catch (Exception e)
   { System.out.println("Exception: " + e);
   }
 }
}
