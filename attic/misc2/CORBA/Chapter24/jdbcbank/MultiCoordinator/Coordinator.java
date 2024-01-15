package MultiCoordinator;
/**
<p>
<ul>
<li> <b>Java Class</b> MultiCoordinator.Coordinator
<li> <b>Source File</b> MultiCoordinator/Coordinator.java
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
public interface Coordinator extends org.omg.CORBA.Object {
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
  );
  /**
  <p>
  Operation: <b>::MultiCoordinator::Coordinator::start</b>.
  <pre>
    boolean start();
  </pre>
  </p>
  */
  public boolean start();
  /**
  <p>
  Operation: <b>::MultiCoordinator::Coordinator::stop</b>.
  <pre>
    string stop();
  </pre>
  </p>
  */
  public java.lang.String stop();
}
