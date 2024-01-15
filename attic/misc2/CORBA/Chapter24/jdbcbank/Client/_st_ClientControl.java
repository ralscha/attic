package Client;
/**
<p>
<ul>
<li> <b>Java Class</b> Client._st_ClientControl
<li> <b>Source File</b> Client/_st_ClientControl.java
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
public class _st_ClientControl extends org.omg.CORBA.portable.ObjectImpl implements Client.ClientControl {
  protected Client.ClientControl _wrapper = null;
  public Client.ClientControl _this() {
    return this;
  }
  public java.lang.String[] _ids() {
    return __ids;
  }
  private static java.lang.String[] __ids = {
    "IDL:Client/ClientControl:1.0"
  };
  /**
  <p>
  Operation: <b>::Client::ClientControl::start</b>.
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
  Operation: <b>::Client::ClientControl::stop</b>.
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
