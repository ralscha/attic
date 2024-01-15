package MultiCoordinator;
/**
<p>
<ul>
<li> <b>Java Class</b> MultiCoordinator._st_Coordinator
<li> <b>Source File</b> MultiCoordinator/_st_Coordinator.java
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
public class _st_Coordinator extends org.omg.CORBA.portable.ObjectImpl implements MultiCoordinator.Coordinator {
  protected MultiCoordinator.Coordinator _wrapper = null;
  public MultiCoordinator.Coordinator _this() {
    return this;
  }
  public java.lang.String[] _ids() {
    return __ids;
  }
  private static java.lang.String[] __ids = {
    "IDL:MultiCoordinator/Coordinator:1.0"
  };
  /**
  <p>
  Operation: <b>::MultiCoordinator::Coordinator::register</b>.
  <pre>
    boolean register(
      in string clientNumber,
      in ::Client::ClientControl clientObjRef
    );
  </pre>
  </p>
  */
  public boolean register(
    java.lang.String clientNumber,
    Client.ClientControl clientObjRef
  ) {
    try {
      org.omg.CORBA.portable.OutputStream _output = this._request("register", true);
      _output.write_string(clientNumber);
      Client.ClientControlHelper.write(_output, clientObjRef);
      org.omg.CORBA.portable.InputStream _input = this._invoke(_output, null);
      boolean _result;
      _result = _input.read_boolean();
      return _result;
    }
    catch(org.omg.CORBA.TRANSIENT _exception) {
      return register(
        clientNumber,
        clientObjRef
      );
    }
  }
  /**
  <p>
  Operation: <b>::MultiCoordinator::Coordinator::start</b>.
  <pre>
    boolean start();
  </pre>
  </p>
  */
  public boolean start() {
    try {
      org.omg.CORBA.portable.OutputStream _output = this._request("start", true);
      org.omg.CORBA.portable.InputStream _input = this._invoke(_output, null);
      boolean _result;
      _result = _input.read_boolean();
      return _result;
    }
    catch(org.omg.CORBA.TRANSIENT _exception) {
      return start();
    }
  }
  /**
  <p>
  Operation: <b>::MultiCoordinator::Coordinator::stop</b>.
  <pre>
    string stop();
  </pre>
  </p>
  */
  public java.lang.String stop() {
    try {
      org.omg.CORBA.portable.OutputStream _output = this._request("stop", true);
      org.omg.CORBA.portable.InputStream _input = this._invoke(_output, null);
      java.lang.String _result;
      _result = _input.read_string();
      return _result;
    }
    catch(org.omg.CORBA.TRANSIENT _exception) {
      return stop();
    }
  }
}
