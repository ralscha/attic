/*
 * File: ./HELLOAPP/HELLOHELPER.JAVA
 * From: HELLO.IDL
 * Date: Sun Aug 23 16:42:49 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package HelloApp;
public class HelloHelper {
     // It is useless to have instances of this class
     private HelloHelper() { }

    public static void write(org.omg.CORBA.portable.OutputStream out, HelloApp.Hello that) {
        out.write_Object(that);
    }
    public static HelloApp.Hello read(org.omg.CORBA.portable.InputStream in) {
        return HelloApp.HelloHelper.narrow(in.read_Object());
    }
   public static HelloApp.Hello extract(org.omg.CORBA.Any a) {
     org.omg.CORBA.portable.InputStream in = a.create_input_stream();
     return read(in);
   }
   public static void insert(org.omg.CORBA.Any a, HelloApp.Hello that) {
     org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
     write(out, that);
     a.read_value(out.create_input_stream(), type());
   }
   private static org.omg.CORBA.TypeCode _tc;
   synchronized public static org.omg.CORBA.TypeCode type() {
          if (_tc == null)
             _tc = org.omg.CORBA.ORB.init().create_interface_tc(id(), "Hello");
      return _tc;
   }
   public static String id() {
       return "IDL:HelloApp/Hello:1.0";
   }
   public static HelloApp.Hello narrow(org.omg.CORBA.Object that)
	    throws org.omg.CORBA.BAD_PARAM {
        if (that == null)
            return null;
        if (that instanceof HelloApp.Hello)
            return (HelloApp.Hello) that;
	if (!that._is_a(id())) {
	    throw new org.omg.CORBA.BAD_PARAM();
	}
        org.omg.CORBA.portable.Delegate dup = ((org.omg.CORBA.portable.ObjectImpl)that)._get_delegate();
        HelloApp.Hello result = new HelloApp._HelloStub(dup);
        return result;
   }
}
