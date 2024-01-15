/**
<p>
<ul>
<li> <b>Java Class</b> _st_EntryListener
<li> <b>Source File</b> _st_EntryListener.java
<li> <b>IDL Absolute Name</b> ::EntryListener
<li> <b>Repository Identifier</b> IDL:EntryListener:1.0
</ul>
<b>IDL definition:</b>
<pre>
    interface EntryListener {
      void newEntryArrived(
        in ::Entry arg0
      );
    };
</pre>
</p>
*/
public class _st_EntryListener extends org.omg.CORBA.portable.ObjectImpl implements EntryListener {
  protected EntryListener _wrapper = null;
  public EntryListener _this() {
    return this;
  }
  public java.lang.String[] _ids() {
    return __ids;
  }
  private static java.lang.String[] __ids = {
    "IDL:EntryListener:1.0"
  };
  /**
  <p>
  Operation: <b>::EntryListener::newEntryArrived</b>.
  <pre>
    void newEntryArrived(
      in ::Entry arg0
    );
  </pre>
  </p>
  */
  public void newEntryArrived(
    Entry arg0
  ) {
    try {
      org.omg.CORBA.portable.OutputStream _output = this._request("newEntryArrived", true);
      _output.write_estruct(arg0, "Entry");
      org.omg.CORBA.portable.InputStream _input = this._invoke(_output, null);
    }
    catch(org.omg.CORBA.TRANSIENT _exception) {
      newEntryArrived(
        arg0
      );
    }
  }
}
