package CounterPortable;
/**
<p>
<ul>
<li> <b>Java Class</b> CounterPortable.CountHelper
<li> <b>Source File</b> CounterPortable/CountHelper.java
<li> <b>IDL Source File</b> Count.idl
<li> <b>IDL Absolute Name</b> ::CounterPortable::Count
<li> <b>Repository Identifier</b> IDL:CounterPortable/Count:1.0
</ul>
<b>IDL definition:</b>
<pre>
    interface Count {
      attribute long sum;
      long increment();
    };
</pre>
</p>
*/
abstract public class CountHelper {
  public static CounterPortable.Count narrow(org.omg.CORBA.Object object) {
    return narrow(object, false);
  }
  private static CounterPortable.Count narrow(org.omg.CORBA.Object object, boolean is_a) {
    if(object == null) {
      return null;
    }
    if(object instanceof CounterPortable.Count) {
      return (CounterPortable.Count) object;
    }
    if(is_a || object._is_a(id())) {
      CounterPortable.Count result = new CounterPortable._st_Count();
      ((org.omg.CORBA.portable.ObjectImpl) result)._set_delegate
        (((org.omg.CORBA.portable.ObjectImpl) object)._get_delegate());
      return result;
    }
    return null;
  }
  public static CounterPortable.Count bind(org.omg.CORBA.ORB orb) {
    return bind(orb, null, null, null);
  }
  public static CounterPortable.Count bind(org.omg.CORBA.ORB orb, java.lang.String name) {
    return bind(orb, name, null, null);
  }
  public static CounterPortable.Count bind(org.omg.CORBA.ORB orb, java.lang.String name, java.lang.String host, org.omg.CORBA.BindOptions options) {
    return narrow(orb.bind(id(), name, host, options), true);
  }
  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }
  public static CounterPortable.Count read(org.omg.CORBA.portable.InputStream _input) {
    return CounterPortable.CountHelper.narrow(_input.read_Object(), true);
  }
  public static void write(org.omg.CORBA.portable.OutputStream _output, CounterPortable.Count value) {
    _output.write_Object(value);
  }
  public static void insert(org.omg.CORBA.Any any, CounterPortable.Count value) {
    org.omg.CORBA.portable.OutputStream output = any.create_output_stream();
    write(output, value);
    any.read_value(output.create_input_stream(), type());
  }
  public static CounterPortable.Count extract(org.omg.CORBA.Any any) {
    if(!any.type().equal(type())) {
      throw new org.omg.CORBA.BAD_TYPECODE();
    }
    return read(any.create_input_stream());
  }
  private static org.omg.CORBA.TypeCode _type;
  public static org.omg.CORBA.TypeCode type() {
    if(_type == null) {
      _type = _orb().create_interface_tc(id(), "Count");
    }
    return _type;
  }
  public static java.lang.String id() {
    return "IDL:CounterPortable/Count:1.0";
  }
}
