package MultiCoordinator;
/**
<p>
<ul>
<li> <b>Java Class</b> MultiCoordinator._example_Coordinator
<li> <b>Source File</b> MultiCoordinator/_example_Coordinator.java
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
public class _example_Coordinator extends MultiCoordinator._CoordinatorImplBase {
  /** Construct a persistently named object. */
  public _example_Coordinator(java.lang.String name) {
    super(name);
  }
  /** Construct a transient object. */
  public _example_Coordinator() {
    super();
  }
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
    // IMPLEMENT: Operation
    return false;
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
    // IMPLEMENT: Operation
    return false;
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
    // IMPLEMENT: Operation
    return null;
  }
}
