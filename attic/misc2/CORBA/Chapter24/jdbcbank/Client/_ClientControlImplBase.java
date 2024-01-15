package Client;
/**
<p>
<ul>
<li> <b>Java Class</b> Client._ClientControlImplBase
<li> <b>Source File</b> Client/_ClientControlImplBase.java
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
abstract public class _ClientControlImplBase extends org.omg.CORBA.portable.Skeleton implements Client.ClientControl {
  protected Client.ClientControl _wrapper = null;
  public Client.ClientControl _this() {
    return this;
  }
  protected _ClientControlImplBase(java.lang.String name) {
    super(name);
  }
  public _ClientControlImplBase() {
  }
  public java.lang.String[] _ids() {
    return __ids;
  }
  private static java.lang.String[] __ids = {
    "IDL:Client/ClientControl:1.0"
  };
  public org.omg.CORBA.portable.MethodPointer[] _methods() {
    org.omg.CORBA.portable.MethodPointer[] methods = {
      new org.omg.CORBA.portable.MethodPointer("start", 0, 0),
      new org.omg.CORBA.portable.MethodPointer("stop", 0, 1),
    };
    return methods;
  }
  public boolean _execute(org.omg.CORBA.portable.MethodPointer method, org.omg.CORBA.portable.InputStream input, org.omg.CORBA.portable.OutputStream output) {
    switch(method.interface_id) {
    case 0: {
      return Client._ClientControlImplBase._execute(_this(), method.method_id, input, output); 
    }
    }
    throw new org.omg.CORBA.MARSHAL();
  }
  public static boolean _execute(Client.ClientControl _self, int _method_id, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.OutputStream _output) {
    switch(_method_id) {
    case 0: {
      boolean _result = _self.start();
      _output.write_boolean(_result);
      return false;
    }
    case 1: {
      java.lang.String _result = _self.stop();
      _output.write_string(_result);
      return false;
    }
    }
    throw new org.omg.CORBA.MARSHAL();
  }
}
