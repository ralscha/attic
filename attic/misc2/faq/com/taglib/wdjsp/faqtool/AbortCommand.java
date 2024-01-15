package com.taglib.wdjsp.faqtool;

import javax.servlet.*;
import javax.servlet.http.*;

public class AbortCommand implements Command {
  private String next;

  public AbortCommand(String next) {
    this.next = next;
  }

  public String execute(HttpServletRequest req)
    throws CommandException {
    req.setAttribute("faqtool.msg", "Operation Aborted");
    return next;
  }

}
