package Bank;
/**
<p>
<ul>
<li> <b>Java Class</b> Bank.DebitCreditHelper
<li> <b>Source File</b> Bank/DebitCreditHelper.java
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
abstract public class DebitCreditHelper {
  public static Bank.DebitCredit narrow(org.omg.CORBA.Object object) {
    return narrow(object, false);
  }
  private static Bank.DebitCredit narrow(org.omg.CORBA.Object object, boolean is_a) {
    if(object == null) {
      return null;
    }
    if(object instanceof Bank.DebitCredit) {
      return (Bank.DebitCredit) object;
    }
    if(is_a || object._is_a(id())) {
      Bank._st_DebitCredit result = (Bank._st_DebitCredit)new Bank._st_DebitCredit();
      ((org.omg.CORBA.portable.ObjectImpl) result)._set_delegate
        (((org.omg.CORBA.portable.ObjectImpl) object)._get_delegate());
      ((org.omg.CORBA.portable.ObjectImpl) result._this())._set_delegate
        (((org.omg.CORBA.portable.ObjectImpl) object)._get_delegate());
      return (Bank.DebitCredit) result._this();
    }
    return null;
  }
  public static Bank.DebitCredit bind(org.omg.CORBA.ORB orb) {
    return bind(orb, null, null, null);
  }
  public static Bank.DebitCredit bind(org.omg.CORBA.ORB orb, java.lang.String name) {
    return bind(orb, name, null, null);
  }
  public static Bank.DebitCredit bind(org.omg.CORBA.ORB orb, java.lang.String name, java.lang.String host, org.omg.CORBA.BindOptions options) {
    return narrow(orb.bind(id(), name, host, options), true);
  }
  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }
  public static Bank.DebitCredit read(org.omg.CORBA.portable.InputStream _input) {
    return Bank.DebitCreditHelper.narrow(_input.read_Object(), true);
  }
  public static void write(org.omg.CORBA.portable.OutputStream _output, Bank.DebitCredit value) {
    _output.write_Object(value);
  }
  public static void insert(org.omg.CORBA.Any any, Bank.DebitCredit value) {
    org.omg.CORBA.portable.OutputStream output = any.create_output_stream();
    write(output, value);
    any.read_value(output.create_input_stream(), type());
  }
  public static Bank.DebitCredit extract(org.omg.CORBA.Any any) {
    if(!any.type().equal(type())) {
      throw new org.omg.CORBA.BAD_TYPECODE();
    }
    return read(any.create_input_stream());
  }
  private static org.omg.CORBA.TypeCode _type;
  public static org.omg.CORBA.TypeCode type() {
    if(_type == null) {
      _type = _orb().create_interface_tc(id(), "DebitCredit");
    }
    return _type;
  }
  public static java.lang.String id() {
    return "IDL:Bank/DebitCredit:1.0";
  }
}
