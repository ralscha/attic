package Bank;
/**
<p>
<ul>
<li> <b>Java Class</b> Bank.DebitCredit
<li> <b>Source File</b> Bank/DebitCredit.java
<li> <b>IDL Source File</b> DebitCredit.idl
<li> <b>IDL Absolute Name</b> ::Bank::DebitCredit
<li> <b>Repository Identifier</b> IDL:Bank/DebitCredit:1.0
</ul>
<b>IDL definition:</b>
<pre>
    interface DebitCredit {
      void debitCreditTransaction(
        in double random1,
        in double random2,
        in boolean staticTxn
      )
      raises(
        ::Bank::BankException
      );
    };
</pre>
</p>
*/
public interface DebitCredit extends org.omg.CORBA.Object {
  /**
  <p>
  Operation: <b>::Bank::DebitCredit::debitCreditTransaction</b>.
  <pre>
    void debitCreditTransaction(
      in double random1,
      in double random2,
      in boolean staticTxn
    )
    raises(
      ::Bank::BankException
    );
  </pre>
  </p>
  */
  public void debitCreditTransaction(
    double random1,
    double random2,
    boolean staticTxn
  ) throws
    Bank.BankException;
}
