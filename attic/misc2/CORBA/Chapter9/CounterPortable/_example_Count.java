package CounterPortable;
/**
<p>
<ul>
<li> <b>Java Class</b> CounterPortable._example_Count
<li> <b>Source File</b> CounterPortable/_example_Count.java
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
public class _example_Count extends CounterPortable._CountImplBase {
  /** Construct a persistently named object. */
  public _example_Count(java.lang.String name) {
    super(name);
  }
  /** Construct a transient object. */
  public _example_Count() {
    super();
  }
  /**
  <p>
  Operation: <b>::CounterPortable::Count::increment</b>.
  <pre>
    long increment();
  </pre>
  </p>
  */
  public int increment() {
    // implement operation...
    return 0;
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
    // implement attribute writer...
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
    // implement attribute reader...
    return 0;
  }
}
