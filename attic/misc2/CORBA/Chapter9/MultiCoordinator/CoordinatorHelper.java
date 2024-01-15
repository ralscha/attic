package MultiCoordinator;
/**
<p>
<ul>
<li> <b>Java Class</b> MultiCoordinator.CoordinatorHelper
<li> <b>Source File</b> MultiCoordinator/CoordinatorHelper.java
<li> <b>IDL Source File</b> Coordinator.idl
<li> <b>IDL Absolute Name</b> ::MultiCoordinator::Coordinator
<li> <b>Repository Identifier</b> IDL:MultiCoordinator/Coordinator:1.0
</ul>
<b>IDL definition:</b>
<pre>
    interface Coordinator {
      boolean register(
        in string clientNumber,
        in ::Client::ClientControl clientObjRef
      );
      boolean start();
      string stop();
    };
</pre>
</p>
*/
abstract public class CoordinatorHelper {
  public static MultiCoordinator.Coordinator narrow(org.omg.CORBA.Object object) {
    return narrow(object, false);
  }
  private static MultiCoordinator.Coordinator narrow(org.omg.CORBA.Object object, boolean is_a) {
    if(object == null) {
      return null;
    }
    if(object instanceof MultiCoordinator.Coordinator) {
      return (MultiCoordinator.Coordinator) object;
    }
    if(is_a || object._is_a(id())) {
      MultiCoordinator.Coordinator result = new MultiCoordinator._st_Coordinator();
      ((org.omg.CORBA.portable.ObjectImpl) result)._set_delegate
        (((org.omg.CORBA.portable.ObjectImpl) object)._get_delegate());
      return result;
    }
    return null;
  }
  public static MultiCoordinator.Coordinator bind(org.omg.CORBA.ORB orb) {
    return bind(orb, null, null, null);
  }
  public static MultiCoordinator.Coordinator bind(org.omg.CORBA.ORB orb, java.lang.String name) {
    return bind(orb, name, null, null);
  }
  public static MultiCoordinator.Coordinator bind(org.omg.CORBA.ORB orb, java.lang.String name, java.lang.String host, org.omg.CORBA.BindOptions options) {
    return narrow(orb.bind(id(), name, host, options), true);
  }
  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }
  public static MultiCoordinator.Coordinator read(org.omg.CORBA.portable.InputStream _input) {
    return MultiCoordinator.CoordinatorHelper.narrow(_input.read_Object(), true);
  }
  public static void write(org.omg.CORBA.portable.OutputStream _output, MultiCoordinator.Coordinator value) {
    _output.write_Object(value);
  }
  public static void insert(org.omg.CORBA.Any any, MultiCoordinator.Coordinator value) {
    org.omg.CORBA.portable.OutputStream output = any.create_output_stream();
    write(output, value);
    any.read_value(output.create_input_stream(), type());
  }
  public static MultiCoordinator.Coordinator extract(org.omg.CORBA.Any any) {
    if(!any.type().equal(type())) {
      throw new org.omg.CORBA.BAD_TYPECODE();
    }
    return read(any.create_input_stream());
  }
  private static org.omg.CORBA.TypeCode _type;
  public static org.omg.CORBA.TypeCode type() {
    if(_type == null) {
      _type = _orb().create_interface_tc(id(), "Coordinator");
    }
    return _type;
  }
  public static java.lang.String id() {
    return "IDL:MultiCoordinator/Coordinator:1.0";
  }
}
