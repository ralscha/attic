package Counter;
/**
<p>
<ul>
<li> <b>Java Class</b> Counter.CountHolder
<li> <b>Source File</b> Counter/CountHolder.java
<li> <b>IDL Source File</b> Count.idl
<li> <b>IDL Absolute Name</b> ::Counter::Count
<li> <b>Repository Identifier</b> IDL:Counter/Count:1.0
</ul>
<b>IDL definition:</b>
<pre>
    interface Count {
      attribute long sum;
      long increment();
    };
</pre>
</p>
*/
final public class CountHolder implements org.omg.CORBA.portable.Streamable {
  public Counter.Count value;
  public CountHolder() {
  }
  public CountHolder(Counter.Count value) {
    this.value = value;
  }
  public void _read(org.omg.CORBA.portable.InputStream input) {
    value = CountHelper.read(input);
  }
  public void _write(org.omg.CORBA.portable.OutputStream output) {
    CountHelper.write(output, value);
  }
  public org.omg.CORBA.TypeCode _type() {
    return CountHelper.type();
  }
}
