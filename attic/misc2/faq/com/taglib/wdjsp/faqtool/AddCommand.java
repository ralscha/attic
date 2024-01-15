package com.taglib.wdjsp.faqtool;

import javax.servlet.*;
import javax.servlet.http.*;

public class AddCommand implements Command {
  private String next;

  public AddCommand(String next) {
    this.next = next;
  }
  
  public String execute(HttpServletRequest req)
    throws CommandException {
    try {
      if (CommandToken.isValid(req)) {
	FaqRepository faqs = FaqRepository.getInstance();
	FaqBean faq = new FaqBean();
	faq.setQuestion(req.getParameter("question"));
	faq.setAnswer(req.getParameter("answer"));
	faqs.put(faq);
	req.setAttribute("faqtool.msg", "FAQ Added Successfully");
      }
      else {
	req.setAttribute("faqtool.msg", "Invalid Reload Attempted");
      }
      return next;
    }
    catch (FaqRepositoryException fe) {
      throw new CommandException("AddCommand: " + fe.getMessage());
    }    
  }
}
