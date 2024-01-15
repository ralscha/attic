
public class Money {
  private int fAmount;
  private String fCurrency;

  public Money(int amount, String currency) {
    this.fAmount = amount;
    this.fCurrency = currency;
  }

  public int amount() {
    return fAmount;
  }

  public String currency() {
    return fCurrency;
  }

  public Money add(Money m) {
    return new Money(amount()+m.amount(), currency());
  }

  public boolean equals(Object anObject) {
    if (! (anObject instanceof Money)) 
        return false;

    Money aMoney = (Money)anObject;
    return aMoney.currency().equals(currency()) && amount() == aMoney.amount();
  }

}
