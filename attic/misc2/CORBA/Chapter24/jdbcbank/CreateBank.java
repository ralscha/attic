// CreateBank.java

class CreateBank {

  public static void main(String args[])
  {
    try
    {
      DCBank.BankAdmin myBank = new DCBank.BankAdmin();
      if (args.length == 1)
         myBank.createDB(Integer.parseInt(args[0]));
      else
         myBank.createDB(1);
      System.out.println("Database bank created");

    } catch( Exception e )
    {
      System.out.println("Creation of database bank failed");
      e.printStackTrace();
      System.exit(1);
    }
    System.exit(0);
  }
}

