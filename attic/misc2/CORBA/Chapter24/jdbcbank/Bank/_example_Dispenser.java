package Bank;
/**
<p>
<ul>
<li> <b>Java Class</b> Bank._example_Dispenser
<li> <b>Source File</b> Bank/_example_Dispenser.java
<li> <b>IDL Source File</b> DebitCredit.idl
<li> <b>IDL Absolute Name</b> ::Bank::Dispenser
<li> <b>Repository Identifier</b> IDL:Bank/Dispenser:1.0
</ul>
<b>IDL definition:</b>
<pre>
    interface Dispenser {
      ::Bank::DebitCredit reserveDebitCreditObject(
      )
      raises(
        ::Bank::BankException
      );
      void releaseDebitCreditObject(
        in ::Bank::DebitCredit debitCreditObject
      )
      raises(
        ::Bank::BankException
      );
    };
</pre>
</p>
*/
public class _example_Dispenser extends Bank._DispenserImplBase {
  /** Construct a persistently named object. */
  public _example_Dispenser(java.lang.String name) {
    super(name);
  }
  /** Construct a transient object. */
  public _example_Dispenser() {
    super();
  }
  /**
  <p>
  Operation: <b>::Bank::Dispenser::reserveDebitCreditObject</b>.
  <pre>
    ::Bank::DebitCredit reserveDebitCreditObject(
    )
    raises(
      ::Bank::BankException
    );
  </pre>
  </p>
  */
  public Bank.DebitCredit reserveDebitCreditObject(
  ) throws
    Bank.BankException {
    // IMPLEMENT: Operation
    return null;
  }
  /**
  <p>
  Operation: <b>::Bank::Dispenser::releaseDebitCreditObject</b>.
  <pre>
    void releaseDebitCreditObject(
      in ::Bank::DebitCredit debitCreditObject
    )
    raises(
      ::Bank::BankException
    );
  </pre>
  </p>
  */
  public void releaseDebitCreditObject(
    Bank.DebitCredit debitCreditObject
  ) throws
    Bank.BankException {
    // IMPLEMENT: Operation
  }
}
