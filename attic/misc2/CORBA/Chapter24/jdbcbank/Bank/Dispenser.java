package Bank;
/**
<p>
<ul>
<li> <b>Java Class</b> Bank.Dispenser
<li> <b>Source File</b> Bank/Dispenser.java
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
public interface Dispenser extends org.omg.CORBA.Object {
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
    Bank.BankException;
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
    Bank.BankException;
}
