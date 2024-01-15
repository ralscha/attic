package ch.ess.mvs.db4o;



public class Beginlist {

  private int word1;
  private int word2;
  private int total;

  public Beginlist(int word1, int word2, int total) {
    this.word1 = word1;
    this.word2 = word2;
    this.total = total;
  }

  public Beginlist() {
    //no action      
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getWord1() {
    return word1;
  }

  public void setWord1(int word1) {
    this.word1 = word1;
  }

  public int getWord2() {
    return word2;
  }

  public void setWord2(int word2) {
    this.word2 = word2;
  }

}
