package com.taglib.wdjsp.faqtool;

import javax.servlet.*;
import javax.servlet.http.*;
  
public class GetCommand implements Command {
  private String next;

  public GetCommand(String next) {
    this.next = next;
  }
  
  public String execute(HttpServletRequest req)
    throws CommandException {
    try {
      FaqRepository faqs = FaqRepository.getInstance();
      int id = Integer.parseInt(req.getParameter("id"));
      FaqBean faq = faqs.getFaq(id);
      req.setAttribute("faq", faq);
      return next;
    }
    catch (NumberFormatException e) {
      throw new CommandException("GetCommand: invalid ID");
    }
    catch (UnknownFaqException uf) {
      throw new CommandException("GetCommand: " + uf.getMessage());
    }
    catch (FaqRepositoryException fe) {
      throw new CommandException("GetCommand: " + fe.getMessage());
    }
  }

}
