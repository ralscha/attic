package com.taglib.wdjsp.faqtool;

public class UnknownFaqException extends FaqRepositoryException {
  
  public UnknownFaqException() {
    super();
  }

  public UnknownFaqException(String msg) {
    super(msg);
  }
}
