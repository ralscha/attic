/**
<p>
<ul>
<li> <b>Java Class</b> _st_IGuestBook
<li> <b>Source File</b> _st_IGuestBook.java
<li> <b>IDL Absolute Name</b> ::IGuestBook
<li> <b>Repository Identifier</b> IDL:IGuestBook:1.0
</ul>
<b>IDL definition:</b>
<pre>
    interface IGuestBook {
      void addEntry(
        in ::EntryListener arg0,
        in ::Entry arg1
      );
      ::java::util::Vector getEntries();
      void addEntryListener(
        in ::EntryListener arg0
      );
      void removeEntryListener(
        in ::EntryListener arg0
      );
    };
</pre>
</p>
*/
public class _st_IGuestBook extends org.omg.CORBA.portable.ObjectImpl implements IGuestBook {
  protected IGuestBook _wrapper = null;
  public IGuestBook _this() {
    return this;
  }
  public java.lang.String[] _ids() {
    return __ids;
  }
  private static java.lang.String[] __ids = {
    "IDL:IGuestBook:1.0"
  };
  /**
  <p>
  Operation: <b>::IGuestBook::addEntry</b>.
  <pre>
    void addEntry(
      in ::EntryListener arg0,
      in ::Entry arg1
    );
  </pre>
  </p>
  */
  public void addEntry(
    EntryListener arg0,
    Entry arg1
  ) {
    try {
      org.omg.CORBA.portable.OutputStream _output = this._request("addEntry", true);
      EntryListenerHelper.write(_output, arg0);
      _output.write_estruct(arg1, "Entry");
      org.omg.CORBA.portable.InputStream _input = this._invoke(_output, null);
    }
    catch(org.omg.CORBA.TRANSIENT _exception) {
      addEntry(
        arg0,
        arg1
      );
    }
  }
  /**
  <p>
  Operation: <b>::IGuestBook::getEntries</b>.
  <pre>
    ::java::util::Vector getEntries();
  </pre>
  </p>
  */
  public java.util.Vector getEntries() {
    try {
      org.omg.CORBA.portable.OutputStream _output = this._request("getEntries", true);
      org.omg.CORBA.portable.InputStream _input = this._invoke(_output, null);
      java.util.Vector _result;
      _result = (java.util.Vector)_input.read_estruct("java.util.Vector");
      return _result;
    }
    catch(org.omg.CORBA.TRANSIENT _exception) {
      return getEntries();
    }
  }
  /**
  <p>
  Operation: <b>::IGuestBook::addEntryListener</b>.
  <pre>
    void addEntryListener(
      in ::EntryListener arg0
    );
  </pre>
  </p>
  */
  public void addEntryListener(
    EntryListener arg0
  ) {
    try {
      org.omg.CORBA.portable.OutputStream _output = this._request("addEntryListener", true);
      EntryListenerHelper.write(_output, arg0);
      org.omg.CORBA.portable.InputStream _input = this._invoke(_output, null);
    }
    catch(org.omg.CORBA.TRANSIENT _exception) {
      addEntryListener(
        arg0
      );
    }
  }
  /**
  <p>
  Operation: <b>::IGuestBook::removeEntryListener</b>.
  <pre>
    void removeEntryListener(
      in ::EntryListener arg0
    );
  </pre>
  </p>
  */
  public void removeEntryListener(
    EntryListener arg0
  ) {
    try {
      org.omg.CORBA.portable.OutputStream _output = this._request("removeEntryListener", true);
      EntryListenerHelper.write(_output, arg0);
      org.omg.CORBA.portable.InputStream _input = this._invoke(_output, null);
    }
    catch(org.omg.CORBA.TRANSIENT _exception) {
      removeEntryListener(
        arg0
      );
    }
  }
}
