import common.util.jar.*;

public class TestJarUtil
{  
  public static void main(String [] args) throws Exception
  {            
    final String JARNAME = "d:/javaprojects/private/common_examples/jarapp.jar";
    
    // display all classes in JARNAME jar 
    SuffixZipEntryFilter classFilter = new SuffixZipEntryFilter(".class");
    JarInfo jinfo = new JarInfo(JARNAME, classFilter);
    System.out.println( jinfo );
        
    // create a jar class loader to read from JARNAME
    JarInfoClassLoader jcl = new JarInfoClassLoader(JARNAME);        
    
    // and load a class from that class loader
    Class c = jcl.loadClass("TestJarUtil");    
    
    // display that class
    System.out.println( c );
  }        
}