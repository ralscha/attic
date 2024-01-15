package MultiCoordinator;
/**
<p>
<ul>
<li> <b>Java Class</b> MultiCoordinator._sk_Coordinator
<li> <b>Source File</b> MultiCoordinator/_sk_Coordinator.java
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
// Warning: 
//   This class has been deprecated by the new IDL to Java Language mapping.
//   Please use the new implementation base class: _CoordinatorImplBase
abstract public class _sk_Coordinator extends _CoordinatorImplBase {
  protected _sk_Coordinator(java.lang.String name) {
    super(name);
  }
  protected _sk_Coordinator() {
  }
}
