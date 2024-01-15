package CounterPortable;
/**
<p>
<ul>
<li> <b>Java Class</b> CounterPortable.CountHolder
<li> <b>Source File</b> CounterPortable/CountHolder.java
<li> <b>IDL Source File</b> Count.idl
<li> <b>IDL Absolute Name</b> ::CounterPortable::Count
<li> <b>Repository Identifier</b> IDL:CounterPortable/Count:1.0
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
  public CounterPortable.Count value;
  public CountHolder() {
  }
  public CountHolder(CounterPortable.Count value) {
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
