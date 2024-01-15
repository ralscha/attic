package ch.ess.mvs.db4o;

import java.io.Serializable;

public class Wordlist implements Serializable {

  private int hash;
  private String word;

  public Wordlist(int hash, java.lang.String word) {
    this.hash = hash;
    this.word = word;
  }

  public Wordlist() {
    //no action
  }

  public int getHash() {
    return hash;
  }

  public void setHash(int hash) {
    this.hash = hash;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

}
