package Client;
/**
<p>
<ul>
<li> <b>Java Class</b> Client._example_ClientControl
<li> <b>Source File</b> Client/_example_ClientControl.java
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
public class _example_ClientControl extends Client._ClientControlImplBase {
  /** Construct a persistently named object. */
  public _example_ClientControl(java.lang.String name) {
    super(name);
  }
  /** Construct a transient object. */
  public _example_ClientControl() {
    super();
  }
  /**
  <p>
  Operation: <b>::Client::ClientControl::start</b>.
  <pre>
    boolean start();
  </pre>
  </p>
  */
  public boolean start() {
    // implement operation...
    return false;
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
    // implement operation...
    return null;
  }
}
