package Bank;
/**
<p>
<ul>
<li> <b>Java Class</b> Bank.DebitCreditHolder
<li> <b>Source File</b> Bank/DebitCreditHolder.java
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
final public class DebitCreditHolder implements org.omg.CORBA.portable.Streamable {
  public Bank.DebitCredit value;
  public DebitCreditHolder() {
  }
  public DebitCreditHolder(Bank.DebitCredit value) {
    this.value = value;
  }
  public void _read(org.omg.CORBA.portable.InputStream input) {
    value = DebitCreditHelper.read(input);
  }
  public void _write(org.omg.CORBA.portable.OutputStream output) {
    DebitCreditHelper.write(output, value);
  }
  public org.omg.CORBA.TypeCode _type() {
    return DebitCreditHelper.type();
  }
}
