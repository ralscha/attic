package com.taglib.wdjsp.faqtool;

import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateCommand implements Command {
  private String next;

  public UpdateCommand(String next) {
    this.next = next;
  }

  public String execute(HttpServletRequest req)
    throws CommandException {
    try {
      if (CommandToken.isValid(req)) {
	FaqRepository faqs = FaqRepository.getInstance();
	FaqBean faq = new FaqBean();
	faq.setID(Integer.parseInt(req.getParameter("id")));
	faq.setQuestion(req.getParameter("question"));
	faq.setAnswer(req.getParameter("answer"));
	faqs.update(faq);
	req.setAttribute("faqtool.msg", "FAQ Updated Successfully");
      }
      else {
	req.setAttribute("faqtool.msg", "Invalid Reload Attempted");
      }	
      return next;
    }
    catch (NumberFormatException e) {
      throw new CommandException("UpdateCommand: invalid ID");
    }
    catch (UnknownFaqException uf) {
      throw new CommandException("UpdateCommand: "+uf.getMessage());
    }
    catch (FaqRepositoryException fe) {
      throw new CommandException("UpdateCommand: "+fe.getMessage());
    }    
  }

}
