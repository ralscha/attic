package Reflection;

import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.text.*;

public class Test {

  private final static Class STRING_CLASS = String.class;
  private final static Class STRINGARR_CLASS = String[].class;
  private final static Class INTEGER_CLASS = Integer.class;

	public static void main(String[] args) throws Exception {
  
    Integer[][] test = new Integer[1][12];
    test[0][0] = new Integer(0);

    int[][] hour = new int[5][24];
    hour[0][0]++;



    List l = new ArrayList();
    l.add("Müller");
    l.add("abraxis");
    l.add("Zed");
    l.add("Abraxisa");
    l.add("zelda");
    l.add("Überspielen");
    l.add("Mueller");
    l.add("Monat");

    
    Collator collate = Collator.getInstance();
    //collate.setStrength(Collator.PRIMARY);
    System.out.println(collate.equals("Müller", "Mueller"));
    System.out.println(collate.getStrength());
    Collections.sort(l, collate);
    //Collections.sort(l);

    for (int i = 0; i < l.size(); i++) {
      System.out.println(l.get(i));
    }

    /*
    Class destType = new Double(5555).getClass();
    Class origType = new String("1000").getClass();
 	  long start = System.currentTimeMillis();
    int x = 0;
    for (int i = 0; i < 1000000; i++) {

      if ((( destType == Integer.class)
					|| (destType == Long   .class)
					|| (destType == Float  .class)
					|| (destType == Double .class)
					|| (destType == Boolean.class)
					|| (destType == Integer.TYPE )
					|| (destType == Long   .TYPE )
					|| (destType == Float  .TYPE )
					|| (destType == Double .TYPE )
					|| (destType == Boolean.TYPE )
					 ) && 
					 (( origType == Integer.class)
					|| (origType == Long   .class)
					|| (origType == Float  .class)
					|| (origType == Double .class)
					|| (origType == Boolean.class)
					|| (origType == Integer.TYPE )
					|| (origType == Long   .TYPE )
					|| (origType == Float  .TYPE )
					|| (origType == Double .TYPE )
					|| (origType == Boolean.TYPE )
					))  {
        x++;
      }
    }

    System.out.println("TIME : " + (System.currentTimeMillis() - start));
    System.out.println("x = " + x);

*/
   
/*
    Object testString = new Integer(5555);

  	long start = System.currentTimeMillis();
    int x = 0;
    for (int i = 0; i < 10000000; i++) {
      if (testString instanceof String) {
        x++;
      } else if (testString instanceof String[]) {
        x--;
      } else if (testString instanceof Integer) {
        x+=2;
      }
    }
   	System.out.println("TIME instanceof: " + (System.currentTimeMillis() - start));
    System.out.println("x = " + x);
 
   Class compareClass = null;

  	start = System.currentTimeMillis();
    x = 0;
    for (int i = 0; i < 10000000; i++) {
      compareClass = testString.getClass();
      if (compareClass == String.class) {
        x++;
      } else if (compareClass == String[].class) {
        x--;
      } else if (compareClass == Integer.class) {
        x+=2;
      }
    }
   	System.out.println("TIME class: " + (System.currentTimeMillis() - start));
    System.out.println("x = " + x);
 
  	start = System.currentTimeMillis();
    x = 0;

    for (int i = 0; i < 10000000; i++) {
      compareClass = testString.getClass();
      if (compareClass == STRING_CLASS) {
        x++;
      } else if (compareClass == STRINGARR_CLASS) {
        x--;
      } else if (compareClass == INTEGER_CLASS) {
        x+=2;
      }
    }
   	System.out.println("TIME const: " + (System.currentTimeMillis() - start));
    System.out.println("x = " + x);


  	start = System.currentTimeMillis();
    x = 0;

    for (int i = 0; i < 10000000; i++) {
      compareClass = testString.getClass();
      if (compareClass.equals(String.class)) {
        x++;
      } else if (compareClass.equals(String[].class)) {
        x--;
      } else if (compareClass.equals(Integer.class)) {
        x+=2;
      }
    }
   	System.out.println("TIME equals class: " + (System.currentTimeMillis() - start));
    System.out.println("x = " + x);

    start = System.currentTimeMillis();
    x = 0;

    for (int i = 0; i < 10000000; i++) {
      compareClass = testString.getClass();
      if (compareClass.equals(STRING_CLASS)) {
        x++;
      } else if (compareClass.equals(STRINGARR_CLASS)) {
        x--;
      } else if (compareClass.equals(INTEGER_CLASS)) {
        x+=2;
      }
    }
    System.out.println("TIME equals const: " + (System.currentTimeMillis() - start));
    System.out.println("x = " + x);
*/
  
  /*

    long start = System.currentTimeMillis();
    for (int i = 0; i < 100000; i++) {
      Method reader = ReflectionUtil.getPropertyReader(test, "name");

      if (reader != null) {
        Object result = reader.invoke(test, null);
        //System.out.println(result);
        //System.out.println(result.getClass());
      } else {
        System.out.println("no reader found");
      }
    }
    System.out.println("TIME FIRST: " + (System.currentTimeMillis() - start));
  
    Method writer = ReflectionUtil.getPropertyWriter(test, "male");
    Class[] parameters = writer.getParameterTypes();
    if (parameters.length == 1) {
      if (parameters[0] == boolean.class) {
        Object[] params = new Object[1];
        params[0] = Boolean.FALSE;
        writer.invoke(test, params);
        System.out.println("AFTER = " + test.isMale()); 
      }      
    }

    String property = "name";
    start = System.currentTimeMillis();

    for (int i = 0; i < 100000; i++) {
      String methodName = "get" + capitalize(property);

	    Class paramTypes[] = new Class[0];
	    Method method = null;
	    try {
         method = test.getClass().getMethod(methodName, paramTypes);
         Object result = method.invoke(test, null);
         //System.out.println(result);
         //System.out.println(result.getClass());	
	    } catch (Exception e) {
        System.err.println(e);
	    }
    }
    System.out.println("TIME SECOND: " + (System.currentTimeMillis() - start));  
*/
	}

	
	public static String capitalize(String name) {

    if ((name == null) || (name.length() < 1))
	      return (name);
    char chars[] = name.toCharArray();
    chars[0] = Character.toUpperCase(chars[0]);
    return new String(chars);

	}

}