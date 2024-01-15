package com.taglib.wdjsp.faqtool;

import javax.servlet.*;
import javax.servlet.http.*;

public class GetAllCommand implements Command {
  private String next;

  public GetAllCommand(String next) {
    this.next = next;
  }

  public String execute(HttpServletRequest req)
    throws CommandException {
    try {
      FaqRepository faqs = FaqRepository.getInstance();
      FaqBean[] faqList = faqs.getFaqs();
      req.setAttribute("faqs", faqList);
      return next;
    }
    catch (FaqRepositoryException fe) {
      throw new CommandException("GetCommand: " + fe.getMessage());
    }
  }

}
