/**
<p>
<ul>
<li> <b>Java Class</b> EntryListenerHolder
<li> <b>Source File</b> EntryListenerHolder.java
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
final public class EntryListenerHolder implements org.omg.CORBA.portable.Streamable {
  public EntryListener value;
  public EntryListenerHolder() {
  }
  public EntryListenerHolder(EntryListener value) {
    this.value = value;
  }
  public void _read(org.omg.CORBA.portable.InputStream input) {
    value = EntryListenerHelper.read(input);
  }
  public void _write(org.omg.CORBA.portable.OutputStream output) {
    EntryListenerHelper.write(output, value);
  }
  public org.omg.CORBA.TypeCode _type() {
    return EntryListenerHelper.type();
  }
}
