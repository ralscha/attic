/*
 * File: ./COUNTERPORTABLE/COUNTHELPER.JAVA
 * From: COUNT.IDL
 * Date: Sat Aug 22 06:54:29 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package CounterPortable;
public class CountHelper {
     // It is useless to have instances of this class
     private CountHelper() { }

    public static void write(org.omg.CORBA.portable.OutputStream out, CounterPortable.Count that) {
        out.write_Object(that);
    }
    public static CounterPortable.Count read(org.omg.CORBA.portable.InputStream in) {
        return CounterPortable.CountHelper.narrow(in.read_Object());
    }
   public static CounterPortable.Count extract(org.omg.CORBA.Any a) {
     org.omg.CORBA.portable.InputStream in = a.create_input_stream();
     return read(in);
   }
   public static void insert(org.omg.CORBA.Any a, CounterPortable.Count that) {
     org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
     write(out, that);
     a.read_value(out.create_input_stream(), type());
   }
   private static org.omg.CORBA.TypeCode _tc;
   synchronized public static org.omg.CORBA.TypeCode type() {
          if (_tc == null)
             _tc = org.omg.CORBA.ORB.init().create_interface_tc(id(), "Count");
      return _tc;
   }
   public static String id() {
       return "IDL:CounterPortable/Count:1.0";
   }
   public static CounterPortable.Count narrow(org.omg.CORBA.Object that)
	    throws org.omg.CORBA.BAD_PARAM {
        if (that == null)
            return null;
        if (that instanceof CounterPortable.Count)
            return (CounterPortable.Count) that;
	if (!that._is_a(id())) {
	    throw new org.omg.CORBA.BAD_PARAM();
	}
        org.omg.CORBA.portable.Delegate dup = ((org.omg.CORBA.portable.ObjectImpl)that)._get_delegate();
        CounterPortable.Count result = new CounterPortable._CountStub(dup);
        return result;
   }
}
