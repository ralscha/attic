package ch.ess.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.base.model.Document;

public abstract class AbstractDocumentAction extends Action {

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    HttpDocument httpDoc = new HttpDocument(getContent(request));
    httpDoc.write(response);

    return null;

  }

  protected abstract Document getContent(HttpServletRequest request) throws Exception;

}
