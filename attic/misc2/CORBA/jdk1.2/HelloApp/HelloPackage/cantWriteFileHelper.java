/*
 * File: ./HELLOAPP/HELLOPACKAGE/CANTWRITEFILEHELPER.JAVA
 * From: HELLO.IDL
 * Date: Sun Aug 23 16:42:49 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package HelloApp.HelloPackage;
public class cantWriteFileHelper {
     // It is useless to have instances of this class
     private cantWriteFileHelper() { }

    public static void write(org.omg.CORBA.portable.OutputStream out, HelloApp.HelloPackage.cantWriteFile that) {
    out.write_string(id());
 }
    public static HelloApp.HelloPackage.cantWriteFile read(org.omg.CORBA.portable.InputStream in) {
        HelloApp.HelloPackage.cantWriteFile that = new HelloApp.HelloPackage.cantWriteFile();
         // read and discard the repository id
        in.read_string();
    return that;
    }
   public static HelloApp.HelloPackage.cantWriteFile extract(org.omg.CORBA.Any a) {
     org.omg.CORBA.portable.InputStream in = a.create_input_stream();
     return read(in);
   }
   public static void insert(org.omg.CORBA.Any a, HelloApp.HelloPackage.cantWriteFile that) {
     org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
     write(out, that);
     a.read_value(out.create_input_stream(), type());
   }
   private static org.omg.CORBA.TypeCode _tc;
   synchronized public static org.omg.CORBA.TypeCode type() {
       int _memberCount = 0;
       org.omg.CORBA.StructMember[] _members = null;
          if (_tc == null) {
               _members= new org.omg.CORBA.StructMember[0];
             _tc = org.omg.CORBA.ORB.init().create_exception_tc(id(), "cantWriteFile", _members);
          }
      return _tc;
   }
   public static String id() {
       return "IDL:HelloApp/Hello/cantWriteFile:1.0";
   }
}
