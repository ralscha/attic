package Counter;
/**
<p>
<ul>
<li> <b>Java Class</b> Counter.Count
<li> <b>Source File</b> Counter/Count.java
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
public interface Count extends org.omg.CORBA.Object {
  /**
  <p>
  Writer for attribute: <b>::Counter::Count::sum</b>.
  <pre>
    attribute long sum;
  </pre>
  </p>
  */
  public void sum(int sum);
  /**
  <p>
  Reader for attribute: <b>::Counter::Count::sum</b>.
  <pre>
    attribute long sum;
  </pre>
  </p>
  */
  public int sum();
  /**
  <p>
  Operation: <b>::Counter::Count::increment</b>.
  <pre>
    long increment();
  </pre>
  </p>
  */
  public int increment();
}
