package ch.ess.lbw.web.reports;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.lbw.dao.LieferantDao;
import ch.ess.lbw.model.Lieferant;

@SpringAction
@StrutsAction(path = "/zertifizierungen", forwards = @Forward(name = "success", path = "/zertifizierungen.jsp"))
public class ZertifizierungenAction extends Action {

  private LieferantDao lieferantDao;

  public void setLieferantDao(LieferantDao lieferantDao) {
    this.lieferantDao = lieferantDao;
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    int totalIso = 0;
    int totalIsoKeine = 0;
    int totalIsoGeplant = 0;
    
    int totalVda = 0;
    int totalVdaGeplant = 0;
    int totalVdaKeine = 0;
    
    int totalQs = 0;
    int totalQsGeplant = 0;
    int totalQsKeine = 0;
    
    int totalISO14001 = 0;
    int totalISO14001Keine = 0;
    
    int totalOekoAudit = 0;
    int totalOekoAuditKeine = 0;
    
    int totalISOTS16949 = 0;
    int totalISOTS16949Keine = 0;
    
    for (Lieferant lieferant : lieferantDao.findAll()) {
      if ("9001".equals(lieferant.getIso())) {
        totalIso++;
      } else if ((lieferant.getIsoGesellschaft() != null) && 
          (lieferant.getIsoGesellschaft().contains("plan") &&
              !lieferant.getIsoGesellschaft().contains("nicht"))) {
        totalIsoGeplant++;
      } else {
        totalIsoKeine++;
      }
      
      if ((lieferant.getVda() != null) && (lieferant.getVda())) {
        totalVda++;
      } else if ((lieferant.getVdaBemerkung() != null) && 
          (lieferant.getVdaBemerkung().contains("plan") && 
              !lieferant.getVdaBemerkung().contains("nicht"))) {
        totalVdaGeplant++;
      } else {
        totalVdaKeine++;
      }
      
      if ((lieferant.getQs() != null) && (lieferant.getQs())) {
        totalQs++;
      } else if ((lieferant.getQsBemerkung() != null) && 
          (lieferant.getQsBemerkung().contains("plan") && 
              !lieferant.getQsBemerkung().contains("nicht"))) {
        totalQsGeplant++;
      } else {
        totalQsKeine++;
      }
      
      if ((lieferant.getIso14001() != null) && (lieferant.getIso14001())) {
        totalISO14001++;
      } else {
        totalISO14001Keine++;
      }
      
      
      if ((lieferant.getOekoAudit() != null) && (lieferant.getOekoAudit())) {
        totalOekoAudit++;
      } else {
        totalOekoAuditKeine++;
      }
      
      if ((lieferant.getIsoTs16949() != null) && (lieferant.getIsoTs16949())) {
        totalISOTS16949++;
      } else {
        totalISOTS16949Keine++;
      }
      
    }
    
    ZertifizierungenProducer producer = new ZertifizierungenProducer();
    producer.setValue("ISO 9001", totalIso);
    producer.setValue("geplant", totalIsoGeplant);
    producer.setValue("keine", totalIsoKeine);
    request.getSession().setAttribute("isoZertifizierungProducer", producer);

    producer = new ZertifizierungenProducer();
    producer.setValue("VDA 6.1", totalVda);
    producer.setValue("geplant", totalVdaGeplant);
    producer.setValue("ohne", totalVdaKeine);
    request.getSession().setAttribute("vdaZertifizierungProducer", producer);
    
    producer = new ZertifizierungenProducer();
    producer.setValue("QS 9000", totalQs);
    producer.setValue("geplant", totalQsGeplant);
    producer.setValue("ohne", totalQsKeine);
    request.getSession().setAttribute("qsZertifizierungProducer", producer);
    
    
    producer = new ZertifizierungenProducer();
    producer.setValue("ISO 14001", totalISO14001);
    producer.setValue("ohne", totalISO14001Keine);
    request.getSession().setAttribute("iso14001ZertifizierungProducer", producer);
    
    producer = new ZertifizierungenProducer();
    producer.setValue("Oeko-Audit", totalOekoAudit);
    producer.setValue("ohne", totalOekoAuditKeine);
    request.getSession().setAttribute("oekoAuditZertifizierungProducer", producer);
        
    producer = new ZertifizierungenProducer();
    producer.setValue("ISO/TS16949", totalISOTS16949);
    producer.setValue("ohne", totalISOTS16949Keine);
    request.getSession().setAttribute("iso16949ZertifizierungProducer", producer);
    
    request.getSession().setAttribute("selectedTab", "iso");
    
    return mapping.findForward("success");

  }

}
