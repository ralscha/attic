package CounterPortable;
/**
<p>
<ul>
<li> <b>Java Class</b> CounterPortable._sk_Count
<li> <b>Source File</b> CounterPortable/_sk_Count.java
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
// Warning: 
//   This class has been deprecated by the new IDL to Java Language mapping.
//   Please use the new implementation base class: _CountImplBase
abstract public class _sk_Count extends _CountImplBase {
  protected _sk_Count(java.lang.String name) {
    super(name);
  }
  protected _sk_Count() {
  }
}
