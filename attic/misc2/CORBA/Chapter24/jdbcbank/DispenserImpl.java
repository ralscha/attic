public class DispenserImpl extends Bank._DispenserImplBase
{
  private int maxObjects = 10;
  private int numObjects = 0;
  private DebitCreditStatus[] debitCredit =
                      new DebitCreditStatus[maxObjects];

  public DispenserImpl(java.lang.String[] args,
                       java.lang.String name, int num)
  {
    super(name);

    try
    {
      // get reference to orb
      org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);

      // prestart n DebitCredit Objects
      numObjects = num;
      for (int i=0; i < numObjects; i++)
      {
        debitCredit[i] = new DebitCreditStatus();
        debitCredit[i].ref = new DebitCreditImpl(args[0], "DebitCredit" + (i+1));
        orb.connect(debitCredit[i].ref);
      }
    } catch(org.omg.CORBA.SystemException e)
    { System.err.println(e);
    }

  }


  public Bank.DebitCredit reserveDebitCreditObject( )
                    throws Bank.BankException
                           {
    for (int i=0; i < numObjects; i++)
    {
      if (!debitCredit[i].inUse)
         { debitCredit[i].inUse = true;
           System.out.println("DebitCredit" + (i+1) + " reserved.");
           return debitCredit[i].ref;
         }
    }
    return null;
  }

  public void releaseDebitCreditObject(
                    Bank.DebitCredit debitCreditObject)
                    throws Bank.BankException
  {
    for (int i=0; i < numObjects; i++)
    {
      if (debitCredit[i].ref == debitCreditObject)
         { debitCredit[i].inUse = false;
           System.out.println("DebitCredit" + (i+1) + " released.");
           return;
         }
    }
    System.out.println("Reserved Object not found");
    return;
  }
}


class DebitCreditStatus
{
  DebitCreditImpl ref;
  boolean         inUse;

  DebitCreditStatus()
  { ref = null;
    inUse = false;
  }
}
