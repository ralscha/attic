package Bank;
/**
<p>
<ul>
<li> <b>Java Class</b> Bank.DispenserHelper
<li> <b>Source File</b> Bank/DispenserHelper.java
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
abstract public class DispenserHelper {
  public static Bank.Dispenser narrow(org.omg.CORBA.Object object) {
    return narrow(object, false);
  }
  private static Bank.Dispenser narrow(org.omg.CORBA.Object object, boolean is_a) {
    if(object == null) {
      return null;
    }
    if(object instanceof Bank.Dispenser) {
      return (Bank.Dispenser) object;
    }
    if(is_a || object._is_a(id())) {
      Bank._st_Dispenser result = (Bank._st_Dispenser)new Bank._st_Dispenser();
      ((org.omg.CORBA.portable.ObjectImpl) result)._set_delegate
        (((org.omg.CORBA.portable.ObjectImpl) object)._get_delegate());
      ((org.omg.CORBA.portable.ObjectImpl) result._this())._set_delegate
        (((org.omg.CORBA.portable.ObjectImpl) object)._get_delegate());
      return (Bank.Dispenser) result._this();
    }
    return null;
  }
  public static Bank.Dispenser bind(org.omg.CORBA.ORB orb) {
    return bind(orb, null, null, null);
  }
  public static Bank.Dispenser bind(org.omg.CORBA.ORB orb, java.lang.String name) {
    return bind(orb, name, null, null);
  }
  public static Bank.Dispenser bind(org.omg.CORBA.ORB orb, java.lang.String name, java.lang.String host, org.omg.CORBA.BindOptions options) {
    return narrow(orb.bind(id(), name, host, options), true);
  }
  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }
  public static Bank.Dispenser read(org.omg.CORBA.portable.InputStream _input) {
    return Bank.DispenserHelper.narrow(_input.read_Object(), true);
  }
  public static void write(org.omg.CORBA.portable.OutputStream _output, Bank.Dispenser value) {
    _output.write_Object(value);
  }
  public static void insert(org.omg.CORBA.Any any, Bank.Dispenser value) {
    org.omg.CORBA.portable.OutputStream output = any.create_output_stream();
    write(output, value);
    any.read_value(output.create_input_stream(), type());
  }
  public static Bank.Dispenser extract(org.omg.CORBA.Any any) {
    if(!any.type().equal(type())) {
      throw new org.omg.CORBA.BAD_TYPECODE();
    }
    return read(any.create_input_stream());
  }
  private static org.omg.CORBA.TypeCode _type;
  public static org.omg.CORBA.TypeCode type() {
    if(_type == null) {
      _type = _orb().create_interface_tc(id(), "Dispenser");
    }
    return _type;
  }
  public static java.lang.String id() {
    return "IDL:Bank/Dispenser:1.0";
  }
}
