package com.taglib.wdjsp.faqtool;
import javax.servlet.*;
import javax.servlet.http.*;

public interface Command {
  public String execute(HttpServletRequest req)
    throws CommandException;
}
