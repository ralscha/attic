package com.taglib.wdjsp.faqtool;

import javax.servlet.*;
import javax.servlet.http.*;

public class DeleteCommand implements Command {
  private String next;

  public DeleteCommand(String next) {
    this.next = next;
  }

  public String execute(HttpServletRequest req)
    throws CommandException {
    try {
      if (CommandToken.isValid(req)) {
	FaqRepository faqs = FaqRepository.getInstance();
	int id = Integer.parseInt(req.getParameter("id"));
	faqs.removeFaq(id);
	req.setAttribute("faqtool.msg", "FAQ Deleted Successfully");
      }
      else {
	req.setAttribute("faqtool.msg", "Invalid Reload Attempted");
      }
      return next;
    }
    catch (NumberFormatException e) {
      throw new CommandException("DeleteCommand: invalid ID");
    }
    catch (UnknownFaqException u) {
      throw new CommandException("DeleteCommand: "+u.getMessage());
    }
    catch (FaqRepositoryException fe) {
      throw new CommandException("DeleteCommand: "+fe.getMessage());
    }
  }

}
