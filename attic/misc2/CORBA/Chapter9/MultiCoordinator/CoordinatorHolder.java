package MultiCoordinator;
/**
<p>
<ul>
<li> <b>Java Class</b> MultiCoordinator.CoordinatorHolder
<li> <b>Source File</b> MultiCoordinator/CoordinatorHolder.java
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
final public class CoordinatorHolder implements org.omg.CORBA.portable.Streamable {
  public MultiCoordinator.Coordinator value;
  public CoordinatorHolder() {
  }
  public CoordinatorHolder(MultiCoordinator.Coordinator value) {
    this.value = value;
  }
  public void _read(org.omg.CORBA.portable.InputStream input) {
    value = CoordinatorHelper.read(input);
  }
  public void _write(org.omg.CORBA.portable.OutputStream output) {
    CoordinatorHelper.write(output, value);
  }
  public org.omg.CORBA.TypeCode _type() {
    return CoordinatorHelper.type();
  }
}
