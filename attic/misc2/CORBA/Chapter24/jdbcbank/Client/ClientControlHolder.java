package Client;
/**
<p>
<ul>
<li> <b>Java Class</b> Client.ClientControlHolder
<li> <b>Source File</b> Client/ClientControlHolder.java
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
final public class ClientControlHolder implements org.omg.CORBA.portable.Streamable {
  public Client.ClientControl value;
  public ClientControlHolder() {
  }
  public ClientControlHolder(Client.ClientControl value) {
    this.value = value;
  }
  public void _read(org.omg.CORBA.portable.InputStream input) {
    value = ClientControlHelper.read(input);
  }
  public void _write(org.omg.CORBA.portable.OutputStream output) {
    ClientControlHelper.write(output, value);
  }
  public org.omg.CORBA.TypeCode _type() {
    return ClientControlHelper.type();
  }
}
