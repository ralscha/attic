
import java.lang.reflect.*;

public class MyDynamicProxyClass implements InvocationHandler {
  Object obj;

  public MyDynamicProxyClass(Object obj) {
    this.obj = obj;
  }

  public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
    /*
    try {
    } catch (InvocationTargetException e) {
      throw e.getTargetException();
    } 
    */
    System.out.println(m);
    return null;
  }


  public static Object newInstance(Object obj, Class[] interfaces) {
    return Proxy.newProxyInstance(obj.getClass().getClassLoader(), interfaces, 
                                    new MyDynamicProxyClass(obj));
  }


  public static void main(String[] args) {
    MyDynamicProxyClass obj = new MyDynamicProxyClass(null);
    MyProxyInterface myIf = (MyProxyInterface)MyDynamicProxyClass.newInstance(obj, 
                            new Class[]{MyProxyInterface.class});

    myIf.myMethod();
  }
}