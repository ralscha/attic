/**
<p>
<ul>
<li> <b>Java Class</b> EntryHelper
<li> <b>Source File</b> EntryHelper.java
<li> <b>IDL Absolute Name</b> ::Entry
<li> <b>Repository Identifier</b> IDL:Entry:1.0
</ul>
<b>IDL definition:</b>
<pre>
    extensible struct Entry {
      ::java::text::SimpleDateFormat dateFormat;
      string name;
      string message;
      string time;
    };
</pre>
</p>
*/
abstract public class EntryHelper {
  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }
  public static Entry read(org.omg.CORBA.portable.InputStream _input) {
    return (Entry) _input.read_estruct("Entry");
  }
  public static void write(org.omg.CORBA.portable.OutputStream _output, Entry value) {
    _output.write_estruct(value, "Entry");
  }
  public static void insert(org.omg.CORBA.Any any, Entry value) {
    org.omg.CORBA.portable.OutputStream output = any.create_output_stream();
    write(output, value);
    any.read_value(output.create_input_stream(), type());
  }
  public static Entry extract(org.omg.CORBA.Any any) {
    if(!any.type().equal(type())) {
      throw new org.omg.CORBA.BAD_TYPECODE();
    }
    return read(any.create_input_stream());
  }
  private static org.omg.CORBA.TypeCode _type;
  public static org.omg.CORBA.TypeCode type() {
    if(_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[4];
      members[0] = new org.omg.CORBA.StructMember("dateFormat", _orb().create_estruct_tc("IDL:java/text/SimpleDateFormat:1.0", "SimpleDateFormat", null, new org.omg.CORBA.StructMember[0]), null);
      members[1] = new org.omg.CORBA.StructMember("name", _orb().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[2] = new org.omg.CORBA.StructMember("message", _orb().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[3] = new org.omg.CORBA.StructMember("time", _orb().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      _type = _orb().create_estruct_tc(id(), "Entry", null, members);
    }
    return _type;
  }
  public static java.lang.String id() {
    return "IDL:Entry:1.0";
  }
}
