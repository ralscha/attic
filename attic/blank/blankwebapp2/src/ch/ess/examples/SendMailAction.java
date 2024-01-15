package ch.ess.examples;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.ess.common.Constants;
import ch.ess.common.service.mail.MailMessage;
import ch.ess.common.service.mail.MailSender;
import ch.ess.common.web.BaseAction;
import ch.ess.common.web.WebContext;

public class SendMailAction extends BaseAction {

  public ActionForward execute() throws Exception {

    SendMailForm sendMailForm = (SendMailForm) WebContext.currentContext().getForm();

    WebApplicationContext wactx = WebApplicationContextUtils.getWebApplicationContext(WebContext.currentContext()
        .getServletContext());
    MailSender sender = (MailSender) wactx.getBean("MailSender");

    MailMessage mm = new MailMessage();
    mm.setSubject(sendMailForm.getSubject());
    mm.setText(sendMailForm.getText());
    mm.addTo(sendMailForm.getRecipient());

    sender.send(mm);

    addOneMessageRequest(new ActionMessage("userconfig.sentSuccessful"));

    return findForward(Constants.FORWARD_SUCCESS);

  }

}