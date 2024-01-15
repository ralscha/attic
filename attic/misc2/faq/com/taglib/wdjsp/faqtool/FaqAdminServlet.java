package com.taglib.wdjsp.faqtool;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class FaqAdminServlet extends HttpServlet {
  private HashMap commands;
  private String error = "error.jsp";
  
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    initCommands();
  }
								  
  public void service(HttpServletRequest req,
		      HttpServletResponse res)
    throws ServletException, IOException {
    String next;
    try {
      Command cmd = lookupCommand(req.getParameter("cmd"));
      next = cmd.execute(req);
      CommandToken.set(req);
    }
    catch (CommandException e) {
      req.setAttribute("javax.servlet.jsp.jspException", e);
      next = error;
    }
    RequestDispatcher rd;
    rd = req.getRequestDispatcher("/jsp/" + next);
    rd.forward(req, res);
	
	 if (req.getAttribute("faq") != null)
		 log(((FaqBean)req.getAttribute("faq")).toString());
  }

  private Command lookupCommand(String cmd)
    throws CommandException {
    if (cmd == null)
      cmd = "main-menu";
    if (commands.containsKey(cmd.toLowerCase()))
      return (Command)commands.get(cmd.toLowerCase());
    else
      throw new CommandException("Invalid Command Identifier");
  }

  private void initCommands() {
    commands = new HashMap();
    commands.put("main-menu", new NullCommand("menu.jsp"));
    commands.put("abort", new AbortCommand("menu.jsp"));
    commands.put("add", new NullCommand("add.jsp"));
    commands.put("do-add", new AddCommand("menu.jsp"));
    commands.put("update-menu", new GetAllCommand("upd_menu.jsp"));
    commands.put("update", new GetCommand("update.jsp"));
    commands.put("do-update", new UpdateCommand("menu.jsp"));
    commands.put("delete-menu", new GetAllCommand("del_menu.jsp"));
    commands.put("delete", new GetCommand("delete.jsp"));
    commands.put("do-delete", new DeleteCommand("menu.jsp"));
  }
}
    
