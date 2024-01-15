// DebitCreditImpl.java

public class DebitCreditImpl extends Bank._DebitCreditImplBase
{
  private DCBank.BankDB myBankDB;
  private String instanceName;

  public DebitCreditImpl(java.lang.String hostName, java.lang.String iName)
  {
    super(iName);
    try
    {
      myBankDB = new DCBank.BankDB();
      myBankDB.connect(hostName, "bank", "sa", "");
      System.out.println("BankDB Object " + iName + " Created");
      instanceName = iName;
    } catch (Exception e)
    { System.out.println("System Exception ");
    }
  }


  public void debitCreditTransaction(double random1,
                                     double random2, boolean staticTxn)
              throws Bank.BankException,org.omg.CORBA.SystemException
  {
    try
    {
      myBankDB.debitCreditTransaction(random1, random2, staticTxn);
    } catch (Exception e)
    { System.out.println("System Exception during debitCreditTransaction");
      throw new Bank.BankException("debitCredit Error");
    }
  }
}
