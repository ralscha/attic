package com.taglib.wdjsp.faqtool;

import java.util.Date;

public class FaqBean {
  private int id;
  private String question;
  private String answer;
  private Date lastModified;

  public FaqBean() {
    this.id = 0;
    this.question = "";
    this.answer = "";
    this.lastModified = new Date();
  }

  public void setQuestion(String question) {
    this.question = question;
    this.lastModified = new Date();
  }

  public String getQuestion() {
    return this.question;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
    this.lastModified = new Date();
  }

  public String getAnswer() {
    return this.answer;
  }

  public void setID(int id) {
    this.id = id;
  }

  public int getID() {
    return this.id;
  }

  public Date getLastModified() {
    return this.lastModified;
  }

  public void setLastModified(Date modified) {
    this.lastModified = modified;
  }

  public String toString() {
    return "[" + id + "] " + "Q: " + question + "; A: " +
      answer + "\n";
  }
}

