package ch.ess.cal.web.event;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.cal.service.StringInputStream;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;

@StrutsAction(path = "/test")
public class TestAction extends FWAction {


  public void doExecute(ActionContext ctx) throws Exception {
    
    System.out.println(ctx.request().toString());

    BufferedReader br = ctx.request().getReader();
    String line;
//    String user = br.readLine();
//    String password = br.readLine();
    StringBuilder sb = new StringBuilder();
    
    while((line = br.readLine()) != null) {
      sb.append(line);
      sb.append("\n");
    }
    
    
    CalendarBuilder builder = new CalendarBuilder();
    Calendar calendar = builder.build(new StringInputStream(sb.toString()));
    calendar.validate();
    
    System.out.println(sb.toString());
    
    ctx.response().setContentType("text/xml");

    
    OutputStream out = ctx.response().getOutputStream();
    PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
    
    
    pw.println("<result>");
    pw.println("<code>0</code><inserted>1</inserted><updated>5</updated>");
    pw.println("</result>");
       
    pw.flush();
    out.close();
    


    ctx.forwardToResponse();

  }
}