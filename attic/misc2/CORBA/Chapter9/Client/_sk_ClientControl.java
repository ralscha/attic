package Client;
/**
<p>
<ul>
<li> <b>Java Class</b> Client._sk_ClientControl
<li> <b>Source File</b> Client/_sk_ClientControl.java
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
// Warning: 
//   This class has been deprecated by the new IDL to Java Language mapping.
//   Please use the new implementation base class: _ClientControlImplBase
abstract public class _sk_ClientControl extends _ClientControlImplBase {
  protected _sk_ClientControl(java.lang.String name) {
    super(name);
  }
  protected _sk_ClientControl() {
  }
}
