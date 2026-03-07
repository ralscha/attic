package ch.rasc.rulebook;
public class ApplicantBean {
  private int creditScore;
  private double cashOnHand;
  private boolean firstTimeHomeBuyer;

  public ApplicantBean(int creditScore, double cashOnHand, boolean firstTimeHomeBuyer) {
    this.creditScore = creditScore;
    this.cashOnHand = cashOnHand;
    this.firstTimeHomeBuyer = firstTimeHomeBuyer;
  }

  public int getCreditScore() {
    return this.creditScore;
  }

  public void setCreditScore(int creditScore) {     
    this.creditScore = creditScore;
  }

  public double getCashOnHand() {
    return this.cashOnHand;
  }

  public void setCashOnHand(double cashOnHand) {
    this.cashOnHand = cashOnHand;
  }

  public boolean isFirstTimeHomeBuyer() {
    return this.firstTimeHomeBuyer;
  }

  public void setFirstTimeHomeBuyer(boolean firstTimeHomeBuyer) {
    this.firstTimeHomeBuyer = firstTimeHomeBuyer;
  }
}