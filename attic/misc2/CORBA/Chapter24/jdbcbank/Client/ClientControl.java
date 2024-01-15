package Client;
/**
<p>
<ul>
<li> <b>Java Class</b> Client.ClientControl
<li> <b>Source File</b> Client/ClientControl.java
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
public interface ClientControl extends org.omg.CORBA.Object {
  /**
  <p>
  Operation: <b>::Client::ClientControl::start</b>.
  <pre>
    boolean start();
  </pre>
  </p>
  */
  public boolean start();
  /**
  <p>
  Operation: <b>::Client::ClientControl::stop</b>.
  <pre>
    string stop();
  </pre>
  </p>
  */
  public java.lang.String stop();
}
