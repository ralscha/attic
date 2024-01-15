package MultiCoordinator;
/**
<p>
<ul>
<li> <b>Java Class</b> MultiCoordinator._CoordinatorImplBase
<li> <b>Source File</b> MultiCoordinator/_CoordinatorImplBase.java
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
abstract public class _CoordinatorImplBase extends org.omg.CORBA.portable.Skeleton implements MultiCoordinator.Coordinator {
  protected MultiCoordinator.Coordinator _wrapper = null;
  public MultiCoordinator.Coordinator _this() {
    return this;
  }
  protected _CoordinatorImplBase(java.lang.String name) {
    super(name);
  }
  public _CoordinatorImplBase() {
  }
  public java.lang.String[] _ids() {
    return __ids;
  }
  private static java.lang.String[] __ids = {
    "IDL:MultiCoordinator/Coordinator:1.0"
  };
  public org.omg.CORBA.portable.MethodPointer[] _methods() {
    org.omg.CORBA.portable.MethodPointer[] methods = {
      new org.omg.CORBA.portable.MethodPointer("register", 0, 0),
      new org.omg.CORBA.portable.MethodPointer("start", 0, 1),
      new org.omg.CORBA.portable.MethodPointer("stop", 0, 2),
    };
    return methods;
  }
  public boolean _execute(org.omg.CORBA.portable.MethodPointer method, org.omg.CORBA.portable.InputStream input, org.omg.CORBA.portable.OutputStream output) {
    switch(method.interface_id) {
    case 0: {
      return MultiCoordinator._CoordinatorImplBase._execute(_this(), method.method_id, input, output); 
    }
    }
    throw new org.omg.CORBA.MARSHAL();
  }
  public static boolean _execute(MultiCoordinator.Coordinator _self, int _method_id, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.OutputStream _output) {
    switch(_method_id) {
    case 0: {
      java.lang.String clientNumber;
      clientNumber = _input.read_string();
      Client.ClientControl clientObjRef;
      clientObjRef = Client.ClientControlHelper.read(_input);
      boolean _result = _self.register(clientNumber,clientObjRef);
      _output.write_boolean(_result);
      return false;
    }
    case 1: {
      boolean _result = _self.start();
      _output.write_boolean(_result);
      return false;
    }
    case 2: {
      java.lang.String _result = _self.stop();
      _output.write_string(_result);
      return false;
    }
    }
    throw new org.omg.CORBA.MARSHAL();
  }
}
