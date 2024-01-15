/**
<p>
<ul>
<li> <b>Java Class</b> IGuestBookHelper
<li> <b>Source File</b> IGuestBookHelper.java
<li> <b>IDL Absolute Name</b> ::IGuestBook
<li> <b>Repository Identifier</b> IDL:IGuestBook:1.0
</ul>
<b>IDL definition:</b>
<pre>
    interface IGuestBook {
      void addEntry(
        in ::EntryListener arg0,
        in ::Entry arg1
      );
      ::java::util::Vector getEntries();
      void addEntryListener(
        in ::EntryListener arg0
      );
      void removeEntryListener(
        in ::EntryListener arg0
      );
    };
</pre>
</p>
*/
abstract public class IGuestBookHelper {
  public static IGuestBook narrow(org.omg.CORBA.Object object) {
    return narrow(object, false);
  }
  private static IGuestBook narrow(org.omg.CORBA.Object object, boolean is_a) {
    if(object == null) {
      return null;
    }
    if(object instanceof IGuestBook) {
      return (IGuestBook) object;
    }
    if(is_a || object._is_a(id())) {
      _st_IGuestBook result = (_st_IGuestBook)new _st_IGuestBook();
      ((org.omg.CORBA.portable.ObjectImpl) result)._set_delegate
        (((org.omg.CORBA.portable.ObjectImpl) object)._get_delegate());
      ((org.omg.CORBA.portable.ObjectImpl) result._this())._set_delegate
        (((org.omg.CORBA.portable.ObjectImpl) object)._get_delegate());
      return (IGuestBook) result._this();
    }
    return null;
  }
  public static IGuestBook bind(org.omg.CORBA.ORB orb) {
    return bind(orb, null, null, null);
  }
  public static IGuestBook bind(org.omg.CORBA.ORB orb, java.lang.String name) {
    return bind(orb, name, null, null);
  }
  public static IGuestBook bind(org.omg.CORBA.ORB orb, java.lang.String name, java.lang.String host, org.omg.CORBA.BindOptions options) {
    return narrow(orb.bind(id(), name, host, options), true);
  }
  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }
  public static IGuestBook read(org.omg.CORBA.portable.InputStream _input) {
    return IGuestBookHelper.narrow(_input.read_Object(), true);
  }
  public static void write(org.omg.CORBA.portable.OutputStream _output, IGuestBook value) {
    _output.write_Object(value);
  }
  public static void insert(org.omg.CORBA.Any any, IGuestBook value) {
    org.omg.CORBA.portable.OutputStream output = any.create_output_stream();
    write(output, value);
    any.read_value(output.create_input_stream(), type());
  }
  public static IGuestBook extract(org.omg.CORBA.Any any) {
    if(!any.type().equal(type())) {
      throw new org.omg.CORBA.BAD_TYPECODE();
    }
    return read(any.create_input_stream());
  }
  private static org.omg.CORBA.TypeCode _type;
  public static org.omg.CORBA.TypeCode type() {
    if(_type == null) {
      _type = _orb().create_interface_tc(id(), "IGuestBook");
    }
    return _type;
  }
  public static java.lang.String id() {
    return "IDL:IGuestBook:1.0";
  }
}
