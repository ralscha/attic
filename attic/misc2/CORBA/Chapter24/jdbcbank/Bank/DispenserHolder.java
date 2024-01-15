package Bank;
/**
<p>
<ul>
<li> <b>Java Class</b> Bank.DispenserHolder
<li> <b>Source File</b> Bank/DispenserHolder.java
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
final public class DispenserHolder implements org.omg.CORBA.portable.Streamable {
  public Bank.Dispenser value;
  public DispenserHolder() {
  }
  public DispenserHolder(Bank.Dispenser value) {
    this.value = value;
  }
  public void _read(org.omg.CORBA.portable.InputStream input) {
    value = DispenserHelper.read(input);
  }
  public void _write(org.omg.CORBA.portable.OutputStream output) {
    DispenserHelper.write(output, value);
  }
  public org.omg.CORBA.TypeCode _type() {
    return DispenserHelper.type();
  }
}
