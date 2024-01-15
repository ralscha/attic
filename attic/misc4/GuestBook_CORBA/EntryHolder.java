/**
<p>
<ul>
<li> <b>Java Class</b> EntryHolder
<li> <b>Source File</b> EntryHolder.java
<li> <b>IDL Absolute Name</b> ::Entry
<li> <b>Repository Identifier</b> IDL:Entry:1.0
</ul>
<b>IDL definition:</b>
<pre>
    extensible struct Entry {
      ::java::text::SimpleDateFormat dateFormat;
      string name;
      string message;
      string time;
    };
</pre>
</p>
*/
final public class EntryHolder implements org.omg.CORBA.portable.Streamable {
  public Entry value;
  public EntryHolder() {
  }
  public EntryHolder(Entry value) {
    this.value = value;
  }
  public void _read(org.omg.CORBA.portable.InputStream input) {
    value = EntryHelper.read(input);
  }
  public void _write(org.omg.CORBA.portable.OutputStream output) {
    EntryHelper.write(output, value);
  }
  public org.omg.CORBA.TypeCode _type() {
    return EntryHelper.type();
  }
}
