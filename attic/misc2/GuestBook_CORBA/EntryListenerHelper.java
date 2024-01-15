/**
<p>
<ul>
<li> <b>Java Class</b> EntryListenerHelper
<li> <b>Source File</b> EntryListenerHelper.java
<li> <b>IDL Absolute Name</b> ::EntryListener
<li> <b>Repository Identifier</b> IDL:EntryListener:1.0
</ul>
<b>IDL definition:</b>
<pre>
    interface EntryListener {
      void newEntryArrived(
        in ::Entry arg0
      );
    };
</pre>
</p>
*/
abstract public class EntryListenerHelper {
  public static EntryListener narrow(org.omg.CORBA.Object object) {
    return narrow(object, false);
  }
  private static EntryListener narrow(org.omg.CORBA.Object object, boolean is_a) {
    if(object == null) {
      return null;
    }
    if(object instanceof EntryListener) {
      return (EntryListener) object;
    }
    if(is_a || object._is_a(id())) {
      _st_EntryListener result = (_st_EntryListener)new _st_EntryListener();
      ((org.omg.CORBA.portable.ObjectImpl) result)._set_delegate
        (((org.omg.CORBA.portable.ObjectImpl) object)._get_delegate());
      ((org.omg.CORBA.portable.ObjectImpl) result._this())._set_delegate
        (((org.omg.CORBA.portable.ObjectImpl) object)._get_delegate());
      return (EntryListener) result._this();
    }
    return null;
  }
  public static EntryListener bind(org.omg.CORBA.ORB orb) {
    return bind(orb, null, null, null);
  }
  public static EntryListener bind(org.omg.CORBA.ORB orb, java.lang.String name) {
    return bind(orb, name, null, null);
  }
  public static EntryListener bind(org.omg.CORBA.ORB orb, java.lang.String name, java.lang.String host, org.omg.CORBA.BindOptions options) {
    return narrow(orb.bind(id(), name, host, options), true);
  }
  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }
  public static EntryListener read(org.omg.CORBA.portable.InputStream _input) {
    return EntryListenerHelper.narrow(_input.read_Object(), true);
  }
  public static void write(org.omg.CORBA.portable.OutputStream _output, EntryListener value) {
    _output.write_Object(value);
  }
  public static void insert(org.omg.CORBA.Any any, EntryListener value) {
    org.omg.CORBA.portable.OutputStream output = any.create_output_stream();
    write(output, value);
    any.read_value(output.create_input_stream(), type());
  }
  public static EntryListener extract(org.omg.CORBA.Any any) {
    if(!any.type().equal(type())) {
      throw new org.omg.CORBA.BAD_TYPECODE();
    }
    return read(any.create_input_stream());
  }
  private static org.omg.CORBA.TypeCode _type;
  public static org.omg.CORBA.TypeCode type() {
    if(_type == null) {
      _type = _orb().create_interface_tc(id(), "EntryListener");
    }
    return _type;
  }
  public static java.lang.String id() {
    return "IDL:EntryListener:1.0";
  }
}
