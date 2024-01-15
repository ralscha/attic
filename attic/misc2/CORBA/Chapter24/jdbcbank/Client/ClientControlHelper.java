package Client;
/**
<p>
<ul>
<li> <b>Java Class</b> Client.ClientControlHelper
<li> <b>Source File</b> Client/ClientControlHelper.java
<li> <b>IDL Source File</b> Coordinator.idl
<li> <b>IDL Absolute Name</b> ::Client::ClientControl
<li> <b>Repository Identifier</b> IDL:Client/ClientControl:1.0
</ul>
<b>IDL definition:</b>
<pre>
    interface ClientControl {
      boolean start();
      string stop();
    };
</pre>
</p>
*/
abstract public class ClientControlHelper {
  public static Client.ClientControl narrow(org.omg.CORBA.Object object) {
    return narrow(object, false);
  }
  private static Client.ClientControl narrow(org.omg.CORBA.Object object, boolean is_a) {
    if(object == null) {
      return null;
    }
    if(object instanceof Client.ClientControl) {
      return (Client.ClientControl) object;
    }
    if(is_a || object._is_a(id())) {
      Client._st_ClientControl result = (Client._st_ClientControl)new Client._st_ClientControl();
      ((org.omg.CORBA.portable.ObjectImpl) result)._set_delegate
        (((org.omg.CORBA.portable.ObjectImpl) object)._get_delegate());
      ((org.omg.CORBA.portable.ObjectImpl) result._this())._set_delegate
        (((org.omg.CORBA.portable.ObjectImpl) object)._get_delegate());
      return (Client.ClientControl) result._this();
    }
    return null;
  }
  public static Client.ClientControl bind(org.omg.CORBA.ORB orb) {
    return bind(orb, null, null, null);
  }
  public static Client.ClientControl bind(org.omg.CORBA.ORB orb, java.lang.String name) {
    return bind(orb, name, null, null);
  }
  public static Client.ClientControl bind(org.omg.CORBA.ORB orb, java.lang.String name, java.lang.String host, org.omg.CORBA.BindOptions options) {
    return narrow(orb.bind(id(), name, host, options), true);
  }
  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }
  public static Client.ClientControl read(org.omg.CORBA.portable.InputStream _input) {
    return Client.ClientControlHelper.narrow(_input.read_Object(), true);
  }
  public static void write(org.omg.CORBA.portable.OutputStream _output, Client.ClientControl value) {
    _output.write_Object(value);
  }
  public static void insert(org.omg.CORBA.Any any, Client.ClientControl value) {
    org.omg.CORBA.portable.OutputStream output = any.create_output_stream();
    write(output, value);
    any.read_value(output.create_input_stream(), type());
  }
  public static Client.ClientControl extract(org.omg.CORBA.Any any) {
    if(!any.type().equal(type())) {
      throw new org.omg.CORBA.BAD_TYPECODE();
    }
    return read(any.create_input_stream());
  }
  private static org.omg.CORBA.TypeCode _type;
  public static org.omg.CORBA.TypeCode type() {
    if(_type == null) {
      _type = _orb().create_interface_tc(id(), "ClientControl");
    }
    return _type;
  }
  public static java.lang.String id() {
    return "IDL:Client/ClientControl:1.0";
  }
}
