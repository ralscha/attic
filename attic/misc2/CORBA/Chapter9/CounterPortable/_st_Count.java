package CounterPortable;
/**
<p>
<ul>
<li> <b>Java Class</b> CounterPortable._st_Count
<li> <b>Source File</b> CounterPortable/_st_Count.java
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
public class _st_Count extends org.omg.CORBA.portable.ObjectImpl implements CounterPortable.Count {
  public java.lang.String[] _ids() {
    return __ids;
  }
  private static java.lang.String[] __ids = {
    "IDL:CounterPortable/Count:1.0"
  };
  /**
  <p>
  Operation: <b>::CounterPortable::Count::increment</b>.
  <pre>
    long increment();
  </pre>
  </p>
  */
  public int increment() {
    try {
      org.omg.CORBA.portable.OutputStream _output = this._request("increment", true);
      org.omg.CORBA.portable.InputStream _input = this._invoke(_output, null);
      int _result;
      _result = _input.read_long();
      return _result;
    }
    catch(org.omg.CORBA.TRANSIENT _exception) {
      return increment();
    }
  }
  /**
  <p>
  Writer for attribute: <b>::CounterPortable::Count::sum</b>.
  <pre>
    attribute long sum;
  </pre>
  </p>
  */
  public void sum(int sum) {
    try {
      org.omg.CORBA.portable.OutputStream _output = this._request("_set_sum", true);
      _output.write_long(sum);
      org.omg.CORBA.portable.InputStream _input = this._invoke(_output, null);
    }
    catch(org.omg.CORBA.TRANSIENT _exception) {
      sum(
        sum
      );
    }
  }
  /**
  <p>
  Reader for attribute: <b>::CounterPortable::Count::sum</b>.
  <pre>
    attribute long sum;
  </pre>
  </p>
  */
  public int sum() {
    try {
      org.omg.CORBA.portable.OutputStream _output = this._request("_get_sum", true);
      org.omg.CORBA.portable.InputStream _input = this._invoke(_output, null);
      int _result;
      _result = _input.read_long();
      return _result;
    }
    catch(org.omg.CORBA.TRANSIENT _exception) {
      return sum();
    }
  }
}
