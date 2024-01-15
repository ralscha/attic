package ch.ess.mvs.db4o;

import java.io.Serializable;

public class Word3 implements Serializable {

  private int word1;
  private int word2;
  private int word3;
  private int hits;

  public Word3(int word1, int word2, int word3, int hits) {
    this.word1 = word1;
    this.word2 = word2;
    this.word3 = word3;
    this.hits = hits;
  }

  public Word3() {
    //no action
  }

  public int getHits() {
    return hits;
  }

  public void setHits(int hits) {
    this.hits = hits;
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

  public int getWord3() {
    return word3;
  }

  public void setWord3(int word3) {
    this.word3 = word3;
  }

}
