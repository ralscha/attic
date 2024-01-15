package ch.ess.addressbook.web.contact;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForward;

import ch.ess.addressbook.db.AttributeEnum;
import ch.ess.addressbook.db.Contact;
import ch.ess.common.Constants;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.WebContext;


/** 
  * @struts.action path="/exportVcard" validate="false"
  */
public class ExportVcardAction extends HibernateAction {

  private final static int LF = 0x0D; /* 0D */
  private final static int CR = 0x0A; /* 0A */
  
  public ActionForward doAction() throws Exception {
    WebContext ctx = WebContext.currentContext();
    
    ctx.getResponse().setContentType("text/x-vcard");

    String idStr = ctx.getRequest().getParameter(Constants.OBJECT_ID);
    
    Long cID = new Long(idStr);
    Contact contact = Contact.load(cID);
          
    OutputStream out = ctx.getResponse().getOutputStream();
    
    
    writeLine(out, "BEGIN:VCARD");
    writeLine(out, "VERSION:2.1");
    
    String fn = "";
    String f = contact.getAttribute(AttributeEnum.FIRST_NAME);
    String m = contact.getAttribute(AttributeEnum.MIDDLE_NAME);
    String l = contact.getAttribute(AttributeEnum.LAST_NAME);
    
    fn += f;
    
    if (fn.length() > 0) {
      fn += " ";
    }
    fn += m;

    if (fn.length() > 0) {
      fn += " ";
    }
    fn += l;      
    writeLine(out, "FN:" + fn);
    
    
    String title = contact.getAttribute(AttributeEnum.TITLE);
    String n = "";
    n += l;
    n += ";";
    n += f;
    n += ";";
    n += m;
    n += ";";
    n += title;
    writeLine(out, "N:" + n);
    
    
    String nick = contact.getAttribute(AttributeEnum.NICK_NAME);
    if (!GenericValidator.isBlankOrNull(nick)) {
      writeLine(out, "NICKNAME:" + nick);
    }
    
    String birth = contact.getAttribute(AttributeEnum.BIRTHDAY);
    if (!GenericValidator.isBlankOrNull(birth)) {
      try {
        Date d = ch.ess.addressbook.Constants.PARSE_DATE_FORMAT.parse(birth);
        writeLine(out, "BDAY:" + ch.ess.addressbook.Constants.ISO_DATE_FORMAT.format(d));
      } catch (ParseException e) {
        //do nothing
      }
    }
    
    String pobox = contact.getAttribute(AttributeEnum.ADDRESS_POBOX);
    String street = contact.getAttribute(AttributeEnum.ADDRESS_STREET);
    String city = contact.getAttribute(AttributeEnum.ADDRESS_CITY);
    String state = contact.getAttribute(AttributeEnum.ADDRESS_STATE);
    String pc = contact.getAttribute(AttributeEnum.ADDRESS_POSTAL_CODE);
    String country = contact.getAttribute(AttributeEnum.ADDRESS_COUNTRY);
    
    String adr = pobox + ";;" + street + ";" + city + ";" + state + ";" + pc + ";" + country;
    if (adr.length() > 6) {
      writeLine(out, "ADR;PREF:" + adr);
    }
    
    pobox = contact.getAttribute(AttributeEnum.HOME_POBOX);
    street = contact.getAttribute(AttributeEnum.HOME_STREET);
    city = contact.getAttribute(AttributeEnum.HOME_CITY);
    state = contact.getAttribute(AttributeEnum.HOME_STATE);
    pc = contact.getAttribute(AttributeEnum.HOME_POSTAL_CODE);
    country = contact.getAttribute(AttributeEnum.HOME_COUNTRY);
    adr = pobox + ";;" + street + ";" + city + ";" + state + ";" + pc + ";" + country;
    if (adr.length() > 6) {
      writeLine(out, "ADR;HOME:" + adr);
    }    
    
    
    
    String phone = contact.getAttribute(AttributeEnum.HOME_NUMBER);
    if (!GenericValidator.isBlankOrNull(phone)) {
      writeLine(out, "TEL;HOME;VOICE:" + phone);
    }
    
    phone = contact.getAttribute(AttributeEnum.HOME_NUMBER_2);
    if (!GenericValidator.isBlankOrNull(phone)) {
      writeLine(out, "TEL;HOME;VOICE:" + phone);
    }    
    
    
    phone = contact.getAttribute(AttributeEnum.FAX);
    if (!GenericValidator.isBlankOrNull(phone)) {
      writeLine(out, "TEL;FAX:" + phone);
    }     
    
    phone = contact.getAttribute(AttributeEnum.MOBILE_NUMBER);
    if (!GenericValidator.isBlankOrNull(phone)) {
      writeLine(out, "TEL;CELL:" + phone);
    }     
    
    phone = contact.getAttribute(AttributeEnum.COMPANY_MAIN_PHONE_NUMBER);
    if (!GenericValidator.isBlankOrNull(phone)) {
      writeLine(out, "TEL;WORK;VOICE:" + phone);
    }     
    
    phone = contact.getAttribute(AttributeEnum.BUSINESS_NUMBER);
    if (!GenericValidator.isBlankOrNull(phone)) {
      writeLine(out, "TEL;WORK;VOICE:" + phone);
    }     
        
    phone = contact.getAttribute(AttributeEnum.OFFICE_NUMBER_2);
    if (!GenericValidator.isBlankOrNull(phone)) {
      writeLine(out, "TEL;WORK;VOICE:" + phone);
    }         
    
    phone = contact.getAttribute(AttributeEnum.OTHER_NUMBER);
    if (!GenericValidator.isBlankOrNull(phone)) {
      writeLine(out, "TEL;VOICE:" + phone);
    }     
    
    
    
    String email = contact.getAttribute(AttributeEnum.EMAIL);
    if (!GenericValidator.isBlankOrNull(email)) {
      writeLine(out, "EMAIL;PREF;INTERNET:" + email);
    }
    
    String org = contact.getAttribute(AttributeEnum.COMPANY_NAME);    
    if (!GenericValidator.isBlankOrNull(org)) {
      writeLine(out, "ORG:" + org);    
    }
    
    String url = contact.getAttribute(AttributeEnum.HOMEPAGE);
    if (!GenericValidator.isBlankOrNull(url)) {
      writeLine(out, "URL:" + url);
    }
    
    
    writeLine(out, "END:VCARD");

       
    
    out.close();
    return null;
  }
  
  
  private void writeLine(OutputStream out, String data) throws IOException {
    out.write(data.getBytes());
    out.write(LF);
    out.write(CR);
    
  }

}