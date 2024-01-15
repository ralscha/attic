/**
<p>
<ul>
<li> <b>Java Class</b> IGuestBookHolder
<li> <b>Source File</b> IGuestBookHolder.java
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
final public class IGuestBookHolder implements org.omg.CORBA.portable.Streamable {
  public IGuestBook value;
  public IGuestBookHolder() {
  }
  public IGuestBookHolder(IGuestBook value) {
    this.value = value;
  }
  public void _read(org.omg.CORBA.portable.InputStream input) {
    value = IGuestBookHelper.read(input);
  }
  public void _write(org.omg.CORBA.portable.OutputStream output) {
    IGuestBookHelper.write(output, value);
  }
  public org.omg.CORBA.TypeCode _type() {
    return IGuestBookHelper.type();
  }
}
